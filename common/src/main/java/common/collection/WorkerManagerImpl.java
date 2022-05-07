package common.collection;




import common.data.Worker;
import common.exceptions.CannotAddException;
import common.exceptions.CollectionException;
import common.exceptions.EmptyCollectionException;
import common.exceptions.NoSuchIdException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;


/**
 * Operates collection.
 */
public abstract class WorkerManagerImpl<T extends Collection<Worker>> implements WorkerManager {
    private T collection;
    private final java.time.LocalDateTime initDate;
    private final Set<Integer> uniqueIds;

    /**
     * Constructor, set start values
     */
    public WorkerManagerImpl() {
        uniqueIds = new ConcurrentSkipListSet<>();
        initDate = java.time.LocalDateTime.now();
    }

    public int generateNextId() {
        if (collection.isEmpty())
            return 1;
        else {
            int id = 1;
            if (uniqueIds.contains(id)) {
                while (uniqueIds.contains(id)) id += 1;
            }
            return id;
        }
    }

    public void sort() {
        //collection = collection.stream().sorted(new Worker.SortingComparator()).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Return collection
     *
     * @return Collection
     */
    public Collection<Worker> getCollection() {
        return collection;
    }

    /**
     * Add element to collection
     *
     * @param worker Element of collection
     */
    public void add(Worker worker) {
        int id = generateNextId();
        uniqueIds.add(id);
        worker.setId(id);
        collection.add(worker);
    }

    public Worker getByID(Integer id){
        assertNotEmpty();
        Optional<Worker> worker = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if(!worker.isPresent()){
            throw new NoSuchIdException(id);
        }
        return worker.get();
    }
    protected void addWithoutIdGeneration(Worker worker){
        uniqueIds.add(worker.getId());
        collection.add(worker);
    }

    protected void removeAll(Collection<Integer> ids){
        Iterator<Integer> iterator = ids.iterator();
        while (iterator.hasNext()){
            Integer id = iterator.next();
            collection.removeIf(worker -> worker.getId()==id);
            iterator.remove();
        }
    }

    /**
     * Get information about collection
     *
     * @return Information
     */
    public String getInfo() {
        return "Database of Worker, size: " + collection.size() + ", initialization date: " + initDate.toString();
    }

    /**
     * Give info about is this ID used
     *
     * @param ID ID
     * @return is it used or not
     */
    public boolean checkID(Integer ID) {
        return uniqueIds.contains(ID);
    }

    public void assertNotEmpty(){
        if(collection.isEmpty()) throw new EmptyCollectionException();
    }
    /**
     * Delete element by ID
     *
     * @param id ID
     */

    public void removeByID(Integer id) {
        assertNotEmpty();
        Optional<Worker> worker = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if(!worker.isPresent()){
            throw new NoSuchIdException(id);
        }
        collection.remove(worker.get());
        uniqueIds.remove(id);
    }

    /**
     * Delete element by ID
     *
     * @param id ID
     */
    public void updateByID(Integer id, Worker newWorker) {
        assertNotEmpty();
        Optional<Worker> worker = collection.stream()
                .filter(w -> w.getId() == id)
                .findFirst();
        if (!worker.isPresent()) {
            throw new NoSuchIdException(id);
        }
        collection.remove(worker.get());
        newWorker.setId(id);
        collection.add(newWorker);
    }

    /**
     * Get size of collection
     *
     * @return Size of collection
     */
    public int getSize() {
        return collection.size();
    }


    public void clear() {
        collection.clear();
        uniqueIds.clear();
    }

    public void removeFirst() {
        assertNotEmpty();
        Iterator<Worker> it =  collection.iterator();
        int id = it.next().getId();
        it.remove();
        uniqueIds.remove(id);
    }

    /**
     * Add if ID of element bigger than max in collection
     *
     * @param worker Element
     */
    public void addIfMax(Worker worker) {
        if (collection.stream()
                .max(Worker::compareTo)
                .filter(w -> w.compareTo(worker) == 1)
                .isPresent()) {
            throw new CannotAddException();
        }
        add(worker);
    }

    /**
     * Add if ID of element smaller than min in collection
     *
     * @param worker Element
     */
    public void addIfMin(Worker worker) {
        if (collection.stream()
                .min(Worker::compareTo)
                .filter(w -> w.compareTo(worker) < 0)
                .isPresent()) {
            throw new CannotAddException();
        }
        add(worker);
    }

    public List<Worker> filterStartsWithName(String start) {
        assertNotEmpty();
        return collection.stream()
                .filter(w -> w.getName().startsWith(start.trim()))
                .collect(Collectors.toList());
    }

    public Map<LocalDate, Integer> groupByEndDate() {
        assertNotEmpty();
        HashMap<LocalDate, Integer> map = new HashMap<>();
        collection.stream()
                .filter((worker -> worker.getEndDate() != null))
                .forEach((worker) -> {
                    LocalDate endDate = worker.getEndDate();
                    if (map.containsKey(endDate)) {
                        Integer q = map.get(endDate);
                        map.replace(endDate, q + 1);
                    } else {
                        map.put(endDate, 1);
                    }
                });
        return map;
    }

    public List<Long> getUniqueSalaries() {
        assertNotEmpty();
        List<Long> salaries = new LinkedList<>();
        salaries = collection.stream()
                .map(Worker::getSalary)
                .distinct()
                .collect(Collectors.toList());
        return salaries;
    }

    @Override
    public void deserializeCollection(String data) {

    }

    @Override
    public String serializeCollection() {
        return null;
    }
}