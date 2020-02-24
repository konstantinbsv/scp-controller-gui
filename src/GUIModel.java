public class GUIModel {

    private static GUIModel model_instance = null;

    private GUIModel() {

    }

    public static GUIModel getInstance() {
        if (model_instance == null) {
                model_instance = new GUIModel();
        }

        return model_instance;
    }
    
}
