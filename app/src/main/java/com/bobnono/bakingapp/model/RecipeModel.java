package com.bobnono.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by user on 2017-08-02.
 */

public class RecipeModel implements Parcelable {
    String TAG = RecipeModel.class.getSimpleName();

    public RecipeModel(){}

    private int id;
    private String name;
    private int servings;
    private String imageLocation;

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();

    public void setId(int id){this.id = id; }
    public int getId(){return this.id; }

    public void setName(String name){this.name = name; }
    public String getName(){return this.name; }

    public void setServings(int servings){this.servings = servings; }
    public int getServings(){return this.servings; }

    public void setImageLocation(String imageLocation){this.imageLocation = imageLocation; }
    public String getImageLocation(){return this.imageLocation; }

    public void setIngredients(Ingredient ingredient){ingredients.add(ingredient); }
    public ArrayList<Ingredient> getAllIngredients(){return ingredients; }
    public Ingredient getIngredient(int index){return ingredients.get(index); }
    public int getIngredientsCount(){return this.ingredients.size(); }

    public void setSteps(Step step){steps.add(step); }
    public ArrayList<Step> getAllSteps(){return steps; }
    public Step getStep(int index){return steps.get(index); }
    public int getStepsCount(){return this.steps.size(); }

    public static class Ingredient implements Parcelable{
        public Ingredient(){}

        private int quantity;
        private String measure;
        private String ingredient;

        public void setQuantity(int quantity) {this.quantity = quantity; }
        public int getQuantity(){return this.quantity; }

        public void setMeasure(String measure) {this.measure = measure; }
        public String getMeasure(){return this.measure; }

        public void setIngredient(String ingredient){this.ingredient = ingredient; }
        public String getIngredient(){return this.ingredient; }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeInt(quantity);
            dest.writeString(measure);
            dest.writeString(ingredient);
        }


        public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
            public Ingredient createFromParcel(Parcel pc) {
                return new Ingredient(pc);
            }
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };

        public Ingredient(Parcel in){
            quantity = in.readInt();
            measure = in.readString();
            ingredient = in.readString();
        }

    }

    public static class Step implements Parcelable{
        public Step(){}

        private int id;
        private String shortDescription;
        private String description;
        private String videoUrl;
        private String thumbnailUrl;

        public void setId(int id){this.id = id; }
        public int getId(){return this.id; }

        public void setShortDescription(String shortDescription){this.shortDescription = shortDescription; }
        public String getShortDescription(){return this.shortDescription; }

        public void setDescription(String description){this.description = description; }
        public String getDescription(){return this.description; }

        public void setVideoUrl(String videoUrl){this.videoUrl = videoUrl; }
        public String getVideoUrl(){return this.videoUrl; }

        public void setThumbnailUrl(String thumbnailUrl){this.thumbnailUrl = thumbnailUrl; }
        public String getThumbnailUrl(){return this.thumbnailUrl; }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeInt(id);
            dest.writeString(shortDescription);
            dest.writeString(description);
            dest.writeString(videoUrl);
            dest.writeString(thumbnailUrl);
        }

        public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
            public Step createFromParcel(Parcel pc) {
                return new Step(pc);
            }
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };

        public Step(Parcel in){
            id = in.readInt();
            shortDescription = in.readString();
            description = in.readString();
            videoUrl = in.readString();
            thumbnailUrl = in.readString();
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(imageLocation);

        Ingredient[] ingredientsArray = ingredients.toArray(new Ingredient[ingredients.size()]);

        dest.writeParcelableArray(ingredientsArray, flag);

        Step[] stepsArray = steps.toArray(new Step[steps.size()]);
        dest.writeParcelableArray(stepsArray, flag);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<RecipeModel> CREATOR = new Parcelable.Creator<RecipeModel>() {
        public RecipeModel createFromParcel(Parcel pc) {
            return new RecipeModel(pc);
        }
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public RecipeModel(Parcel in){
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        imageLocation = in.readString();

        Parcelable[] parcelables = in.readParcelableArray(Ingredient.class.getClassLoader());
        ingredients = new ArrayList<Ingredient>(parcelables.length);
        for (int i = 0; i < parcelables.length; i++) {
            ingredients.add((Ingredient) parcelables[i]);
        }
        parcelables = null;

        parcelables = in.readParcelableArray(Step.class.getClassLoader());
        steps = new ArrayList<Step>(parcelables.length);
        for (int i = 0; i < parcelables.length; i++){
            steps.add((Step) parcelables[i]);
        }

    }
}
