package client;

public interface IngredientQuantityListener {

    public void onQuantityChange(Ingredient ingredient, int quantity, int threshold);
    
}
