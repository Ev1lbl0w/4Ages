package main;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.Terrain;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Node level1 = (Node)assetManager.loadModel("Scenes/Stage_1.j3o");
        rootNode.attachChild(level1);
        Terrain terrain = (Terrain)level1.getChild("Terrain");
        
        flyCam.setMoveSpeed(30f);
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
        
        /** A white, directional light source */ 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
    
        FilterPostProcessor fpp = assetManager.loadFilter("Interface/waterFilter.j3f");
        viewPort.addProcessor(fpp);
        
        //viewPort.setBackgroundColor(new ColorRGBA(135/255f, 206/255f, 235/255f, 1));
        Texture skyBox = assetManager.loadTexture("Textures/skybox.png");
        Spatial sky = SkyFactory.createSky(assetManager, skyBox, skyBox, skyBox, skyBox, skyBox, skyBox);
        rootNode.attachChild(sky);
        
        BatchNode trees = new BatchNode("Trees");
        for(int i = 0; i < 100; i++) {
            int x = FastMath.nextRandomInt(-80, 80);
            int z = FastMath.nextRandomInt(-80, 80);
            float y = terrain.getHeight(new Vector2f(x, z)) - 135;
            
            if(y < 1) {
                i -= 1;
                continue;
            }
            
            Node tree = (Node)assetManager.loadModel("Textures/trees/tree1.j3o");
            tree.setLocalTranslation(x, y, z);
            trees.attachChild(tree);
        }
        
        trees.batch();
        rootNode.attachChild(trees);
        System.out.println(trees.getChildren().size());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
