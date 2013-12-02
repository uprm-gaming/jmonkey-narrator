package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class NarratorTest extends SimpleApplication implements ActionListener
{
    NarratorAppState gameNarrator;
    
    private static final String MAPPING_FIRST_MSG = "narrator first Message";
    private static final String MAPPING_SECOND_MSG = "narrator second Message";
    private static final String MAPPING_THIRD_MSG = "narrator third Message";
    
    private static final Trigger TRIGGER_FIRST_MSG = new KeyTrigger(KeyInput.KEY_1);
    private static final Trigger TRIGGER_SECOND_MSG = new KeyTrigger(KeyInput.KEY_2);
    private static final Trigger TRIGGER_THIRD_MSG = new KeyTrigger(KeyInput.KEY_3);
    
    public static void main(String[] args)
    {
        AppSettings customSettings = new AppSettings(true);
        customSettings.setTitle("Narrator Test");
        customSettings.setResolution(1280, 720);
        
        NarratorTest app = new NarratorTest();
        app.setSettings(customSettings);
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);
        app.start(); // calls simpleInitApp()
    }

    @Override
    public void simpleInitApp() 
    {
        shutDownDefaultHUD();
        initializeNarrator();
        initKeyboardControls();
        displayColoredBox(ColorRGBA.Blue);
        flyCam.setMoveSpeed(15.0f);
    } 
    
    private void shutDownDefaultHUD()
    {
        setDisplayFps(false);
        setDisplayStatView(false);
    }
    
    private void initializeNarrator()
    {
        gameNarrator = NarratorAppState.newInstance(assetManager, guiNode);
        stateManager.attach(gameNarrator);
    }
    
    private void initKeyboardControls() 
    {
        String[] mappings = {MAPPING_FIRST_MSG, MAPPING_SECOND_MSG, MAPPING_THIRD_MSG};
        Trigger[] triggers = {TRIGGER_FIRST_MSG, TRIGGER_SECOND_MSG, TRIGGER_THIRD_MSG};

        for (int i = 0; i < mappings.length; i++) {
            inputManager.addMapping(mappings[i], triggers[i]);
            inputManager.addListener(this, mappings[i]);
        }
    }
    
    private void displayColoredBox(ColorRGBA color)
    {
        Geometry boxGeometry = new Geometry("Box", new Box(1, 1, 1));
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", color);
        boxGeometry.setMaterial(boxMaterial);
        rootNode.attachChild(boxGeometry);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name)
        {
            case MAPPING_FIRST_MSG:
                if (!isPressed)
                    gameNarrator.talk("Hi there. Welcome to Abner Coimbre's blue box demo.",
                                      "Sounds/bluebox1.wav");
                break;
                
            case MAPPING_SECOND_MSG:
                if (!isPressed)
                    gameNarrator.talk("Congratulations! Your narrator program is working perfectly.",
                                       "Sounds/bluebox2.wav");
                break;
                
            case MAPPING_THIRD_MSG:
                if (!isPressed)
                    gameNarrator.talk("You can now implement the narrator to Yaguez Virtual Factory 2.0",
                                      "Sounds/bluebox3.wav");
                break;
                
            default:
                break;
        }
    }
}