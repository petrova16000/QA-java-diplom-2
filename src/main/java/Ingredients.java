import java.util.ArrayList;


public class Ingredients {


    public ArrayList<String> getIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        ingredients.add("61c0c5a71d1f82001bdaaa6f");
        return ingredients;
    }

    public ArrayList<String> getIncorrectIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("61c0c5a71d1f82001bdaaa11");
        ingredients.add("61c0c5a71d1f82001bdaaa11");
        return ingredients;
    }

    public ArrayList<String> getNoIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        return ingredients;
    }
}
