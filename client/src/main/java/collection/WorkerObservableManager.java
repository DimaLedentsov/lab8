package collection;

import common.collection.WorkerManagerImpl;
import common.connection.CollectionOperation;
import common.connection.Response;
import common.data.Worker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WorkerObservableManager extends WorkerManagerImpl<LinkedList<Worker>> {
    private List<Worker> collection;
    public WorkerObservableManager(){
        collection = new LinkedList<>();
    }

    public void applyChanges(Response response){
        CollectionOperation op = response.getCollectionOperation();
        Collection<Worker> changes = response.getCollection();
        if(op==CollectionOperation.ADD){
            for(Worker worker: changes){
                super.add(worker);
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
}
