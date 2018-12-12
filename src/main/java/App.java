import javafx.animation.RotateTransition; 
import javafx.application.Application;
import javafx.event.EventHandler; 

import javafx.scene.Group; 
import javafx.scene.PerspectiveCamera; 
import javafx.scene.Scene;

import javafx.scene.SubScene;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField; 
import javafx.scene.input.KeyEvent; 
import javafx.scene.paint.Color; 
import javafx.scene.paint.PhongMaterial;
 
import javafx.scene.shape.Box;

import javafx.scene.shape.Cylinder;

import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight; 
import javafx.scene.text.Text;  
import javafx.scene.transform.Rotate; 
import javafx.stage.Stage; 
import javafx.util.Duration; 


import javafx.scene.SceneAntialiasing;

import javafx.geometry.Point3D;


import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
         
public class App extends Application{
   
    private long time;
    private final IntegerProperty counter = new SimpleIntegerProperty();

    
    ArrayList<Box> cubes = new ArrayList<>();

    Xform showForm = new Xform();

    Xform cubesForm = new Xform();

    Xform frontForm = new Xform();
    Xform backForm = new Xform();

    Xform upForm = new Xform();
    Xform downForm = new Xform();

    Xform rightForm = new Xform();
    Xform leftForm = new Xform();

    final Group root = new Group();
    final Xform axisGroup = new Xform();
    final Xform moleculeGroup = new Xform();
    final Xform world = new Xform();

    final PerspectiveCamera camera = new PerspectiveCamera(true);

    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();

    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_DISTANCE_Y = -10;
    //private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    //private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;

    private static final double CAMERA_INITIAL_X_ANGLE = 30;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    private static final double HYDROGEN_ANGLE = 104.5;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    public void setAllCubes()
    {
        cubesForm.getChildren().clear();
        upForm.getChildren().clear();

        cubesForm.getChildren().addAll(cubes);

    }

    public void set4Cubes()
    {
        cubesForm.getChildren().clear();
        upForm.getChildren().clear();

        upForm.getChildren().addAll(cubes.get(4));
        upForm.getChildren().addAll(cubes.get(5));
        upForm.getChildren().addAll(cubes.get(6));
        upForm.getChildren().addAll(cubes.get(7));
    }

    private void buildAxes() {
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }

    private void buildCamera() {
        System.out.println("buildCamera()");

        root.getChildren().add(cameraXform);

        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);

        cameraXform3.setRotateZ(0.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        
        camera.setTranslateY(CAMERA_INITIAL_DISTANCE_Y);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);

        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    private void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX); 
                mouseDeltaY = (mousePosY - mouseOldY); 
                
                double modifier = 1.0;
                
                if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                } 
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }     
                if (me.isPrimaryButtonDown()) {
                    //cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);

                    //cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);  

                    setAllCubes();
                    cubesForm.rz.setAngle(cubesForm.rz.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);

                    for(Node box : cubesForm.getChildren())
                    {
                        System.out.println("Node");
                        
                        System.out.println(box.getLayoutX() + " " + box.getLayoutY());
                        
                        System.out.println();
                    }

                    //upForm.rz.setAngle(upForm.rz.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);  
                }
                else if (me.isSecondaryButtonDown()) {
                    /*double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
                    camera.setTranslateZ(newZ);*/
                    set4Cubes();
                    upForm.rz.setAngle(upForm.rz.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);

                    cubesForm.rz.setAngle(cubesForm.rz.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);

                }
                else if (me.isMiddleButtonDown()) {
                    /*cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);  
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);*/

                    //form.rz.setAngle(form.rz.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);
                }
            }
        });
    }

    private void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                        break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        break;
                    case V:
                        moleculeGroup.setVisible(!moleculeGroup.isVisible());
                        break;
                }
            }
        });
    }
    
    @Override
    public void start(Stage primaryStage) {

        System.out.println("start()");

        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);

        // buildScene();
        buildCamera();
        buildAxes();
        buildCube();

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        handleKeyboard(scene, world);
        handleMouse(scene, world);

        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(camera);

        
    
        /*Cylinder axis2 =  new Cylinder(2, 200);
        axis2.setRotationAxis(Rotate.X_AXIS);
        axis2.setRotate(90);
        axis2.setTranslateX(-50);
        axis2.setTranslateY(-50);
        axis2.setMaterial(new PhongMaterial(Color.FIREBRICK));*/
    
        
    
        //Group root = new Group(group);
    
       /* SubScene subScene = new SubScene(root, 600, 400, true, SceneAntialiasing.BALANCED);
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setTranslateX(-200);
        camera.setTranslateY(-200);
        camera.setTranslateZ(-100);
        //camera.setRotationAxis(new Point3D(0, 0.5, 0.5));
        //camera.setRotate(10);
        subScene.setCamera(camera);
        Scene scene = new Scene(new StackPane(subScene), 600, 400, true, SceneAntialiasing.BALANCED);
    
        primaryStage.setScene(scene);
        primaryStage.show();
    
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - time > 30_000_000) {
                    counter.set((counter.get() + 1) % 360);
                    time = now;
                }
            }
        };

        timer.start();*/
    }

    public void buildCube()
    {
        //Rotate rotate = new Rotate(0, -50, -50, 0, Rotate.Z_AXIS);
        //rotate.angleProperty().bind(counter);

        System.out.println("Building Cubes()");
        
        MakeBox(10, 10, 10, Color.BLACK);
        MakeBox(10, -10, 10, Color.BROWN);
        MakeBox(-10, -10, 10, Color.DARKORANGE);
        MakeBox(-10, 10, 10, Color.CYAN);

        MakeBox(10, 10, -10, Color.DARKGREY);
        MakeBox(10, -10, -10, Color.BLUE);
        MakeBox(-10, -10, -10, Color.ANTIQUEWHITE);
        MakeBox(-10, 10, -10, Color.DARKSALMON);

        world.getChildren().addAll(cubesForm);
        world.getChildren().addAll(upForm);
        world.getChildren().addAll(showForm);

    }

    public void MakeBox(double x, double y, double z, Color c)
    {
        Box box = new Box(20, 20, 20);
        cubes.add(box);

        //Setting the position of the box 
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setTranslateZ(z);

        box.setRotationAxis(Rotate.Z_AXIS);

        box.setMaterial(new PhongMaterial(c));
        //box.getTransforms().add(r);

        cubesForm.getChildren().add(box);
    }

   public static void main(String args[]){ 
      launch(args); 
   }
}