package mygame;

import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

public class NarratorAppState extends AbstractAppState
{
    private AssetManager assetManager;
    private BitmapText narratorText;
    private AudioNode narratorAudio;
    
    public NarratorAppState(AssetManager assetManager, Node guiNode)
    {
        initAssetManager(assetManager);
        initNarratorAudio();
        initNarratorText(guiNode);
    }
    
    private void initAssetManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }
    
    private void initNarratorAudio()
    {
        narratorAudio = new AudioNode();
    }
    
    private void initNarratorText(Node guiNode)
    {
        BitmapFont narratorTextFont = assetManager.loadFont("Interface/ArialRoundedMTBold.fnt");
        narratorText = new BitmapText(narratorTextFont);
        narratorText.setSize(narratorTextFont.getCharSet().getRenderedSize());
        narratorText.move(440, 80, 1);
        guiNode.attachChild(narratorText);
    }
    
    public static NarratorAppState newInstance(AssetManager assetManager, Node guiNode)
    {
        return new NarratorAppState(assetManager, guiNode);
    }
    
    public void talk(String text, String audioPathFile)
    {
        talk(text);
        playAudioFile(audioPathFile);
    }
    
    private void talk(String text)
    {
        if (isHidden())
            show();
        narratorText.setText(text);
    }
    
    private void playAudioFile(String path)
    {
        flushAudio();
        narratorAudio = new AudioNode(assetManager, path, false);
        narratorAudio.setPitch(1.1f);
        narratorAudio.play();
    }
    
    private void flushAudio()
    {
        if (narratorAudio != null) 
        {
            narratorAudio.stop();
            narratorAudio = null;
        }
    }
    
    public boolean hasStoppedTalking()
    {
        return narratorAudio.getStatus() == AudioSource.Status.Stopped;
    }

    public void show()
    {
        narratorText.setCullHint(CullHint.Never);
    }
    
    public void hide()
    {
        narratorText.setCullHint(CullHint.Always);
    }
    
    public boolean isHidden()
    {
        return narratorText.getCullHint() == CullHint.Always;
    }

    @Override
    public void update(float tpf)
    {
        if (hasStoppedTalking())
            hide();
    }
}