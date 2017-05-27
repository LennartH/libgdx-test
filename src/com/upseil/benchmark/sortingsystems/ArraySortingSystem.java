package com.upseil.benchmark.sortingsystems;

import java.util.Comparator;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.Array;

public class ArraySortingSystem extends BaseEntitySystem {
    
    private ComponentMapper<Layer> layerMapper;
    
    private Array<Integer> sorted;
    private Comparator<Integer> entitySorter;
//    private SpriteBatch batch;

    public ArraySortingSystem() {
        super(Aspect.all());
    }

    @Override
    protected void initialize() {
        sorted = new Array<Integer>();
        entitySorter = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(layerMapper.get(o1).get(), layerMapper.get(o2).get());
            }
        };
//        batch = new SpriteBatch();
    }
    
    @Override
    protected void begin() {
        sorted.sort(entitySorter);
    }

    @Override
    protected void processSystem() {
//        batch.begin();
        for(Integer eid : sorted) {
            process(eid);
        }
//        batch.end();
    }
    
    private void process(int eid) {
         // Draw entity here
    }

    @Override
    protected void inserted(int eid) {
        sorted.add(eid);
    }

    @Override
    protected void removed(int eid) {
        sorted.removeValue(eid, true);
    }
}
