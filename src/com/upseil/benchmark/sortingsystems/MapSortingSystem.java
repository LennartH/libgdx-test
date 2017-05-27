package com.upseil.benchmark.sortingsystems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.IntSet.IntSetIterator;

@Wire
public class MapSortingSystem extends BaseEntitySystem {
    
    private ComponentMapper<Layer> layerMapper;
    
//    private final SpriteBatch batch;
    private final IntMap<IntSet> entitiesPerLayer;
    private final IntArray layersToRender;
    
    public MapSortingSystem() {
        super(Aspect.all());
        
//        batch = new SpriteBatch();
        entitiesPerLayer = new IntMap<>();
        layersToRender = new IntArray();
    }
    
    @Override
    protected void inserted(int entity) {
        int zIndex = layerMapper.get(entity).get();
        IntSet entities = entitiesPerLayer.get(zIndex);
        if (entities == null) {
            entities = new IntSet();
            entitiesPerLayer.put(zIndex, entities);
            layersToRender.add(zIndex);
        }
        entities.add(entity);
    }
    
    @Override
    protected void removed(int entity) {
        int zIndex = layerMapper.get(entity).get();
        IntSet entities = entitiesPerLayer.get(zIndex);
        if (entities != null) {
            entities.remove(entity);
        }
    }
    
    @Override
    protected void begin() {
        layersToRender.sort();
    }
    
    @Override
    protected void processSystem() {
//        batch.begin();
        for (int i = 0; i < layersToRender.size; i++) {
            IntSetIterator iterator = entitiesPerLayer.get(layersToRender.get(i)).iterator();
            while (iterator.hasNext) {
                renderEntity(iterator.next());
            }
        }
//        batch.end();
    }
    
    private void renderEntity(int entity) {
       
    }
    
}
