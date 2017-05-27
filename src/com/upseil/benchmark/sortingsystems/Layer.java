package com.upseil.benchmark.sortingsystems;

import com.artemis.PooledComponent;

public class Layer extends PooledComponent {
    
    private int layer;

    public int get() {
        return layer;
    }

    public void set(int layer) {
        this.layer = layer;
    }

    @Override
    protected void reset() {
        layer = 0;
    }
    
}
