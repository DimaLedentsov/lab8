package collection;

import common.collection.WorkerManagerImpl;
import common.connection.CollectionOperation;
import common.connection.Response;
import common.data.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class WorkerObservableManager extends WorkerManagerImpl<ObservableList<Worker>> {
    private ObservableList<Worker> collection;
    private Set<Integer> uniqueIds;
    public WorkerObservableManager(){
        collection = FXCollections.observableArrayList();
        uniqueIds = ConcurrentHashMap.newKeySet();
    }

    public Set<Integer> getUniqueIds(){
        return uniqueIds;
    }
    public void applyChanges(Response response){
        CollectionOperation op = response.getCollectionOperation();
        Collection<Worker> changes = response.getCollection();
        if(op==CollectionOperation.ADD){
            for(Worker worker: changes){
                super.addWithoutIdGeneration(worker);
            }
        }
        if(op==CollectionOperation.REMOVE){
            for(Worker worker: changes){
                super.removeByID(worker.getId());
            }
        }
        if(op==CollectionOperation.UPDATE){
            for(Worker worker: changes){
                super.updateByID(worker.getId(),worker);
            }
        }
    }
    public ObservableList<Worker> getCollection(){
        return collection;
    }
}
