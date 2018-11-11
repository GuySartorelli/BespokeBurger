package client;

import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import client.CurrencyTextField.CurrencySymbol;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

/**
 * TextField that only accepts currency values as input.
 * Any text pasted into this text field will be stripped of any non-currency characters so that only the valid characters remain.
 * <p>Valid characters include the symbol corresponding to the chosen enum value from CurrencyTextField.CurrencySymbol followed by a single digit, then a period, then two more digits.</p>
 * <p>CurrencySymbol values with the appendix <b>_OR_NONE</b> are allowed to either have their corresponding symbol at the front of the text value or
 * have no symbol at all. If a CurrencySymbol without that appendix is used, the corresponding symbol must always be at the front of the text value
 * unless there is no text present.
 * The CurrencySymbol value <b>NONE</b> must not have any currency symbol. The CurrencySymbols <b>ANY</b> and <b>ANY_OR_NONE</b> can have any symbol that corresponds
 * with any valid CurrencySymbol value.</p>
 * @see javafx.scene.control.TextField
 * @author Bespoke Burgers
 *
 */
public class CurrencyTextField extends TextField {
    /**Enum representation of valid currency symbols used by the CurrencyTextField class
     * @see CurrencyTextField
     * @author Bespoke Burgers
     */
    public enum CurrencySymbol{
        NONE("", ".", true),
        DOLLARS("$", ".", false),
        DOLLARS_OR_NONE("$", ".", true),
        EURO("€",",", false),
        EURO_OR_NONE("€",",", true),
        POUNDS("£", ".", false),
        POUNDS_OR_NONE("£", ".", true),
        YEN("¥", ".", false),
        YEN_OR_NONE("¥", ".", true),
        ANY("\u00A4", ".", false),
        ANY_OR_NONE("\u00A4", ".", true);
        
        private String symbol;
        private String delimiter;
        private boolean isNoneType;
        private static Set<String> symbols = new HashSet<String>();
        
        static {
            for (CurrencySymbol symbol : CurrencySymbol.values()) {
                if (symbol != NONE) symbols.add(symbol.getSymbol());
            }
        }
        
        CurrencySymbol(String symbol, String delimiter, boolean isNoneType) {
            this.symbol = symbol;
            this.delimiter = delimiter;
            this.isNoneType = isNoneType;
        }
        
        /**Returns a String representation of the symbol.<br>
         * In the case of <b>ANY</b> and <b>ANY_OR_NONE</b> the representation is the universal symbol &#x00A4*/
        public String getSymbol() {
            return symbol;
        }
        
        /**Returns the delimiter used to separate decimals for this currency*/
        public String getDelimiter() {
            return delimiter;
        }
        
        /**Returns a Set of String representations of all valid symbols.*/
        public static Set<String> getSymbols() {
            return symbols;
        }
        
        public boolean isNoneType() {
            return isNoneType;
        }
    }
    
    //See if you can have it auto-correct itself on losing focus.
    //e.g. if only $1 has been entered, have it autocorrect to $1.00
    //don't forget to localise e.g. euro uses 1,00 not 1.00
    
    private CurrencySymbol currencySymbol;
    private Pattern CURRENCY_PATTERN;
    private Pattern NON_CURRENCY_PATTERN;
    private int maxDollarChars = -1;
    
    public CurrencyTextField() {
        this(CurrencySymbol.ANY_OR_NONE);

//        Allow .50 (and when unfocused, add the $0 to make $0.50)
//        Allow $.50 (and when unfocused, add the 0 to make $0.50)?
//        Allow no currency sign on all of them but only add the currency sign on non _OR_NONEs?
    }
    
    public CurrencyTextField(String text) {
        this(CurrencySymbol.ANY_OR_NONE);
        if (!CURRENCY_PATTERN.matcher(text).matches()) throw new IllegalArgumentException("Text must be in valid currency format");
        setText(text);
        //auto-correct if e.g. 1 to $1.00
    }
    
    public CurrencyTextField(CurrencySymbol symbol) {
        super();
        this.currencySymbol = symbol;
        setupRegex();
        setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {

            @Override
            public Change apply(Change change) {
                if (change.isContentChange()) {
                    String newValue = change.getControlNewText();
                    if (currencySymbol.getDelimiter().equals(",")) newValue = newValue.replaceAll(".", ",");
                    else newValue = newValue.replaceAll(",", ".");
                    int newLength = newValue.length();
                    
                    if (!CURRENCY_PATTERN.matcher(newValue).matches()) {
                        newValue = NON_CURRENCY_PATTERN.matcher(newValue).replaceAll("");
                        newLength = newValue.length();
                    }
//                    if (maxChars > 0 && newLength > maxChars) {
//                        newValue = newValue.substring(0, maxChars);
//                    }
                    change.setText(newValue);
                    change.setRange(0, change.getControlText().length());
                }
                return change;
            }
        }));
    }
    
    public CurrencyTextField(CurrencySymbol symbol, String text) {
        this(symbol);
        if (!CURRENCY_PATTERN.matcher(text).matches()) throw new IllegalArgumentException("Text must be in valid currency format");
        setText(text);
        //auto-correct if e.g. 1 to $1.00
    }
    
    /**
     * Sets up the regular expressions which limit which values can be input to the text field
     */
    private void setupRegex() {
        //the specifics of the regex used is explained at the bottom of this file
        String currencyRegex = "";
        String currencySymbols = "";
        if (currencySymbol == CurrencySymbol.ANY || currencySymbol == CurrencySymbol.ANY_OR_NONE) {
            currencyRegex = "[";
            for (String symbol : CurrencySymbol.getSymbols()) {
                currencyRegex += symbol;
                currencySymbols += symbol;
            }
            currencyRegex += "]";
        } else {
            currencyRegex = currencySymbol.getSymbol();
            currencySymbols = currencySymbol.getSymbol();
        }
        if (currencySymbol != CurrencySymbol.NONE && currencySymbol.isNoneType()) currencyRegex += "?";
        
        currencyRegex += "\\d+(\\"+currencySymbol.getDelimiter()+"\\d{0,2})?";
        String nonCurrencyRegex = String.format("[^.,\\d%s]+", currencySymbols);
        if (currencySymbol != CurrencySymbol.NONE) nonCurrencyRegex += String.format("|(?<=.)[%s]+", currencySymbols);
        nonCurrencyRegex += "|(?<=[,.].{0,20})[.,]+|(?<=[,.]\\d{2}.{0,20})\\d+";
        CURRENCY_PATTERN = Pattern.compile(currencyRegex);
        NON_CURRENCY_PATTERN = Pattern.compile(nonCurrencyRegex, Pattern.DOTALL);
    }
    
    /**
     * Returns the CurrencySymbol enum value for this text field
     * @return the CurrencySymbol enum value for this text field
     */
    public CurrencySymbol getCurrencySymbol() {
        return currencySymbol;
    }
    
    /**
     * Sets the maximum number of digits allowed before a decimal point (default unlimited)<br>
     * To reset this to an unlimited number, set it to any negative integer.
     * @param chars int: the maximum number of digits allowed before a decimal point
     */
    public void setMaxDollarChars(int chars) {
        maxDollarChars = chars;
    }
    
    public boolean startsWithSymbol() {
        if (currencySymbol == CurrencySymbol.NONE) return false;
        return getText().startsWith(currencySymbol.getSymbol());
    }
}

/*
REGEX in CurrencyTextField is pretty nuts, so it is explained here.
Note that where in the look-behind I have had to replace the potentially infinite symbols with specific ranges
because Java does not support infinite look-behinds. I have arbitrarily chosen a range of 0-20 which should be more than sufficient
for any copy-pasted values that would be remotely considered reasonable
To check if text IS ALREADY a currency, I use the following:
[$€]?\d+([,.]\d{0,2})?
This says: "match strings where there is zero or one of the currency symbols, followed by at least one digit,
then a period or comma (depending on currency), then between zero and two more digits"

In order to remove characters that are NOT appropriate for a currency string, it gets a little complicated.
I could have done this in four separate steps as follows:
[^.,\d$€]+  catches innapropriate characters
says "match and one or more non-digit, non-comma, non-period, non currency symbol"

(?<=.)[$€]+     catches currency symbols that follow anything (currency symbols should always be first)
says "match one or more currency symbol that follows any character including a new line

(?<=[,.].*)[.,]+      catches commas and periods that are not the first comma or period.
says "match one or more comma or period which follows a comma or period 0 or more of any other character including new lines"

(?<=[,.]\d{2})\d+    catches too many digits after a decimal
says "match any one or more digits preceeded by a comma or period and 2 digits"

OR all in one step:
[^.,\d$€]+|(?<=.)[$€]+|(?<=[,.].*)[.,]+|(?<=[,.]\d{2}.*)\d+
Catches all innapropriate characters, any currency sign that is not the first character, any period or comma that is not the first period or comma,
and any digits that occur after the first two digits that succeed a period or comma.
says "match one or more characters that are not a comma, period, currency symbol, or digit
    OR one or more currency symbol following any character including a new line
    OR one or more period or comma which follows a period or comma and 0 or more of any character including a new line
    OR one or more digits which follow a comma or period and exactly 2 digits and 0 or more of any character including a new line
*/