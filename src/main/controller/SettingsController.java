package controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.ProjectDao;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jdk.internal.util.xml.impl.Input;
import org.junit.internal.runners.statements.RunAfters;
import task.UpdateTask;
import utils.JdbcUtils;

import java.io.*;
import java.util.Properties;

public class SettingsController implements BaseController {
    @FXML
    private TextField urlTF;
    @FXML
    private TextField portTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordPF;
    private IndexController indexController;
    @FXML
    private TextField result;
    @FXML
    private Text projectsCountTF;
    @FXML
    private Text latestIdTF;
    private String url;
    private String port;
    private String username;
    private String password;
    private ProjectDao projectDao = new ProjectDao();

    class InfoUpdater implements Runnable {
        boolean isrunning;
        UpdateTask task;

        public void setTask(UpdateTask task) {
            this.task = task;
        }

        public InfoUpdater(){
            isrunning = true;
            task = null;
        }

        public InfoUpdater(UpdateTask task){
            isrunning = true;
            this.task = task;
        }

        public void stop(){
            isrunning = false;
        }

        @Override
        public void run() {
            while(isrunning && task.getStatus() != -1 && task.getStatus() != 4) {
                setResultText(task.getInfoString());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private InfoUpdater infoUpdater = new InfoUpdater();

    @FXML
    public void updateShort(){
        UpdateTask task = UpdateTask.getInstance();
        if(task.isRunning()){
            result.setText("已有一个更新任务");
        }
        else if (!task.isRunning()){
            task.setIdFrom(Integer.valueOf(latestIdTF.getText()));
            new Thread(task).start();
            infoUpdater.setTask(task);
            new Thread(infoUpdater).start();
        }
    }

    @FXML
    public void updateAll(){
        UpdateTask task = UpdateTask.getInstance();
        if(task.isRunning()){
            result.setText("已有一个更新任务");
        }
        else{
            new Thread(task).start();
        }
    }

    public void init(){
        getSettingsInfo();
        getUpdateInfo();
        setProjectsInfo();
    }

    private void setProjectsInfo(){
        Task<Void> setProjectsInfoTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int projectsCount = projectDao.getProjectsCount();
                projectsCount = projectsCount<0?0:projectsCount;
                long latestId = projectDao.getLatestId();
                latestId = latestId<0?0:latestId;
                projectsCountTF.setText(String.valueOf(projectsCount));
                latestIdTF.setText(String.valueOf(latestId));
                return null;
            }
        };
        new Thread(setProjectsInfoTask).start();
    }

    private void getSettingsInfo(){
        InputStream in = null;
        try{
            in = new FileInputStream("set.ini");
            Properties prop = new Properties();
            prop.load(in);
            urlTF.setText(prop.getProperty("url"));
            portTF.setText(prop.getProperty("port"));
            usernameTF.setText(prop.getProperty("username"));
            passwordPF.setText(prop.getProperty("password"));
            url = prop.getProperty("url");
            port = prop.getProperty("port");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void setResultText(String text){
        result.setText(text);
    }

    public void getUpdateInfo(){
        UpdateTask task = UpdateTask.getInstance();
        if(!task.isRunning())
            return;
        infoUpdater.setTask(task);
        new Thread(infoUpdater).start();
    }

    public IndexController getIndexController() {
        return indexController;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    @Override
    public void destroy() {
        infoUpdater.stop();
    }

    @FXML public void cancel(){
        urlTF.setText(url);
        portTF.setText(port);
        usernameTF.setText(username);
        passwordPF.setText(password);
    }

    @FXML public void confirm(){
        url = urlTF.getText();
        port = portTF.getText();
        username = usernameTF.getText();
        password = passwordPF.getText();

        Properties prop = new Properties();
        prop.setProperty("url", url);
        prop.setProperty("port", port);
        prop.setProperty("username", username);
        prop.setProperty("password", password);
        System.out.println(prop);

        OutputStream out = null;
        try {
            out = new FileOutputStream("set.ini");
            prop.store(out, "settings");
            JdbcUtils.reload();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
