package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import java.util.Timer;
import java.util.TimerTask;

public class NarratorNiftyGui 
{
    private AssetManager assetManager;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private Nifty nifty;
    private Element narratorPanel;
    private Timer timer;
    private AudioNode narratorVoicedText;
    
    private NarratorNiftyGui(AssetManager assetManager, InputManager inputManager, 
                            AudioRenderer audioRenderer, ViewPort guiViewPort) 
    {
        this.assetManager = assetManager;
        this.inputManager = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort = guiViewPort;
        this.timer = new Timer();
        createNiftyScreen();
        initializeNarratorPanel();
    }
    
    private void createNiftyScreen()
    {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start");
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    private void initializeNarratorPanel()
    {
        narratorPanel = getNiftyElement("narrator_panel");
        narratorPanel.hide();
    }
    
    public static NarratorNiftyGui newInstance(AssetManager assetManager, 
                                               InputManager inputManager, 
                                               AudioRenderer audioRenderer, 
                                               ViewPort guiViewPort) 
    {
        return new NarratorNiftyGui(assetManager, inputManager, audioRenderer, guiViewPort);
    }

    public void talk(String text, String audioPathFile) 
    {
        talk(text);
        playAudioFile(audioPathFile);
    }
    
    public void talk(String text)
    {
        if (!narratorPanel.isVisible())
            narratorPanel.show();

        getNiftyElement("narrator_text").getRenderer(TextRenderer.class).setText(text);
        hidePanelAfterDelay(7);
    }
    
    private void playAudioFile(String path)
    {
        flushAudio();
        narratorVoicedText = new AudioNode(assetManager, path, false);
        narratorVoicedText.play();
    }
    
    private void flushAudio()
    {
        if (narratorVoicedText != null) {
            narratorVoicedText.stop();
            narratorVoicedText = null;
        }
    }
    
    private synchronized void hidePanelAfterDelay(int seconds)
    {
        TimerTask timerAction = new TimerTask() {
            @Override
            public void run() 
            {
                narratorPanel.hide();
            }
        };
        timer.schedule(timerAction, seconds*1000);
    }
    
    private Element getNiftyElement(final String id) 
    {
        return nifty.getCurrentScreen().findElementByName(id);
    }
}