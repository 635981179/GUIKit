package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;

public class IndexController{
    @FXML
    private Pane paneSettings;
    @FXML
    private Pane paneInfos;
    @FXML
    private Pane paneProjects;
    @FXML
    private SubScene subScene;
    private BaseController subSceneController;
    @FXML
    private Pane root;

    public void init() throws IOException {
//        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
//        data.addAll(new PieChart.Data("上海点贸信息技术有限公司", 7),
//                new PieChart.Data("南京好排尚科技有限公司", 229),
//                new PieChart.Data("广联达科技股份有限公司", 2),
//                new PieChart.Data("显为智能科技(苏州）有限公司", 16),
//                new PieChart.Data("江苏鼎思科技发展有限公司", 509),
//                new PieChart.Data("深圳市捷成安科技有限公司", 51),
//                new PieChart.Data("苏州创邦伟达电子智能有限公司", 378),
//                new PieChart.Data("苏州慧软智能科技有限公司", 177),
//                new PieChart.Data("苏州星润新型材料工程有限公司", 116),
//                new PieChart.Data("苏州群岭软件系统有限公司", 486));
//        history.setData(data);
//        history.setLabelLineLength(10);
//        history.setLegendSide(Side.LEFT);
//        history.setVisible(true);
        infos();
    }

    private synchronized void bl(String paneName) throws IOException {
        if(paneName.equals("infos")){
            if(subSceneController != null)
                subSceneController.destroy();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/pages/info.fxml"));
            subScene.setRoot(fxmlLoader.load());
            InfoController controller = (InfoController) fxmlLoader.getController();
            subSceneController = controller;
        }
        else if(paneName.equals("settings")){
            if(subSceneController != null)
                subSceneController.destroy();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/pages/settings.fxml"));
            subScene.setRoot(fxmlLoader.load());
            SettingsController controller = (SettingsController) fxmlLoader.getController();
            controller.setIndexController(this);
            controller.init();
            System.out.println(subScene.rootProperty().get());
            subSceneController = controller;
        }
        else if(paneName.equals("projects")){
            if(subSceneController != null)
                subSceneController.destroy();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/pages/projects.fxml"));
            subScene.setRoot(fxmlLoader.load());
            ProjectsController controller = (ProjectsController) fxmlLoader.getController();
            controller.init();
            subSceneController = controller;
        }
    }

    @FXML
    public void settings() throws IOException {
        Pane now = paneSettings;
        Iterator<Node> it = now.getParent().getChildrenUnmodifiable().iterator();
        while(it.hasNext()){
            it.next().setStyle("-fx-background-color:null");
        }
        now.setStyle("-fx-background-color:#DEB887");
        bl("settings");
    }

    @FXML
    public void infos() throws IOException {
        Pane now = paneInfos;
        Iterator<Node> it = now.getParent().getChildrenUnmodifiable().iterator();
        while(it.hasNext()){
            it.next().setStyle("-fx-background-color:null");
        }
        now.setStyle("-fx-background-color:#DEB887");
        bl("infos");
    }

    @FXML
    public void projects() throws IOException {
        Pane now = paneProjects;
        Iterator<Node> it = now.getParent().getChildrenUnmodifiable().iterator();
        while(it.hasNext()){
            it.next().setStyle("-fx-background-color:null");
        }
        now.setStyle("-fx-background-color:#DEB887");
        bl("projects");
    }

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void minsize(){
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setIconified(true);
    }
}
