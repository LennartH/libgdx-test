package com.upseil.benchmark.sortingsystems;

public class BenchmarkConfiguration {
    
    private final int entityCount;
    private final int layerCount;
    
    private final int iterations;
    private final int deletionsPerIteration;
    private final int addingsPerIteration;
    
    public BenchmarkConfiguration(int entityCount, int layerCount, int iterations, int deletionsPerIteration, int addingsPerIteration) {
        this.entityCount = entityCount;
        this.layerCount = layerCount;
        this.iterations = iterations;
        this.deletionsPerIteration = deletionsPerIteration;
        this.addingsPerIteration = addingsPerIteration;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public int getLayerCount() {
        return layerCount;
    }

    public int getIterations() {
        return iterations;
    }

    public int getDeletionsPerIteration() {
        return deletionsPerIteration;
    }

    public int getAddingsPerIteration() {
        return addingsPerIteration;
    }
    
    @Override
    public String toString() {
        return String.format("%d entities, %d layers, %d iterations, %d deletions per iteration, %d addings per iteration",
                             entityCount, layerCount, iterations, deletionsPerIteration, addingsPerIteration);
    }
    
}
