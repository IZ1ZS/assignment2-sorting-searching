import java.util.Arrays;

public class Experiment {
    private final Sorter sorter;
    private final Searcher searcher;

    public Experiment(Sorter sorter, Searcher searcher) {
        this.sorter = sorter;
        this.searcher = searcher;
    }

    public long measureSortTime(int[] arr, String type) {
        long startTime = System.nanoTime();

        if (type.equalsIgnoreCase("basic")) {
            sorter.basicSort(arr);
        } else if (type.equalsIgnoreCase("advanced")) {
            sorter.advancedSort(arr);
        } else {
            throw new IllegalArgumentException("Unknown sorting type: " + type);
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public long measureSearchTime(int[] arr, int target) {
        long startTime = System.nanoTime();
        searcher.search(arr, target);
        long endTime = System.nanoTime();

        return endTime - startTime;
    }

    public void runAllExperiments() {
        int[] sizes = {10, 100, 1000, 5000};

        System.out.println("Assignment 3: Sorting and Searching Algorithm Analysis System");
        System.out.println("Chosen algorithms: Selection Sort, Merge Sort, Binary Search");
        System.out.println();

        for (int size : sizes) {
            runExperimentForDataset("Random", sorter.generateRandomArray(size));
            runExperimentForDataset("Sorted", sorter.generateSortedArray(size));
        }
    }

    private void runExperimentForDataset(String datasetType, int[] originalArray) {
        int size = originalArray.length;

        int[] basicArray = Arrays.copyOf(originalArray, originalArray.length);
        int[] advancedArray = Arrays.copyOf(originalArray, originalArray.length);
        int[] searchArray = Arrays.copyOf(originalArray, originalArray.length);

        sorter.advancedSort(searchArray); // Binary Search requires a sorted array.
        int target = searchArray[searchArray.length / 2];

        long basicTime = measureSortTime(basicArray, "basic");
        long advancedTime = measureSortTime(advancedArray, "advanced");
        long searchTime = measureSearchTime(searchArray, target);
        int foundIndex = searcher.search(searchArray, target);

        System.out.println("Dataset type: " + datasetType + ", Size: " + size);
        System.out.println("Selection Sort time: " + basicTime + " ns");
        System.out.println("Merge Sort time:     " + advancedTime + " ns");
        System.out.println("Binary Search time:  " + searchTime + " ns");
        System.out.println("Target " + target + " found at index: " + foundIndex);

        if (size == 10) {
            System.out.print("Original array: ");
            sorter.printArray(originalArray);
            System.out.print("Sorted array:   ");
            sorter.printArray(advancedArray);
        }

        if (advancedTime < basicTime) {
            System.out.println("Comparison: Merge Sort was faster in this test.");
        } else {
            System.out.println("Comparison: Selection Sort was faster in this test.");
        }

        System.out.println("--------------------------------------------------");
    }
}
