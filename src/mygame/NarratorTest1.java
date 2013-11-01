package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class NarratorTest1 extends SimpleApplication implements ActionListener {
    NarratorNiftyGui gameNarrator;
    
    public static void main(String[] args) 
    {
        AppSettings customSettings = new AppSettings(true);
        customSettings.setTitle("Narrator Test 1");
        customSettings.setResolution(800, 600);
        
        NarratorTest1 app = new NarratorTest1();
        app.setSettings(customSettings);
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);
        app.start();
    }
    
    @Override
    public void simpleInitApp() 
    {
        shutDownDefaultHUD();
        gameNarrator = getGameNarrator();
        createAndDisplayBox();
        initKeyboardControls();
        flyCam.setMoveSpeed(20.0f);
    }
    
    private void shutDownDefaultHUD()
    {
        setDisplayFps(false);
        setDisplayStatView(false);
    }
    
    private NarratorNiftyGui getGameNarrator() 
    {
        return NarratorNiftyGui.newInstance(assetManager, inputManager, 
                                            audioRenderer, guiViewPort);
    }
    
    private void createAndDisplayBox() 
    {
        Geometry boxGeometry = new Geometry("Box", new Box(1, 1, 1));
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", ColorRGBA.Blue);
        boxGeometry.setMaterial(boxMaterial);
        rootNode.attachChild(boxGeometry);
    }
    
    private void initKeyboardControls() 
    {
        inputManager.addMapping("first message", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("second message", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("third message", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addListener(this, "first message", "second message", "third message");
    }
    
    @Override
    public void onAction(String name, boolean isKeyPressed, float tpf) {
        switch (name)
        {
            case "first message":
                if (!isKeyPressed)
                    gameNarrator.talk("Hey there.");
                break;
            case "second message":
                if (!isKeyPressed)
                    gameNarrator.talk("I'm a blue box.");
                break;
            case "third message":
                if (!isKeyPressed)
                    gameNarrator.talk("Are you bored? Because I'm certainly not.");
                break;
            default:
                break;
        }
    }
   
    @Override
    public void simpleUpdate(float tpf) 
    {}
}