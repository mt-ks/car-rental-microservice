package com.mtks.rent.services.grpc;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SagaStepExecutor {

    List<SagaStep<?>> steps = new ArrayList<>();

    public void setSteps(List<SagaStep<?>> steps) {
        this.steps = steps;
    }

    public void execute(){
        System.out.println("executing");
        for (int i = 0; i < steps.size(); i++) {
            SagaStep<?> step = steps.get(i);
            try {
                step.commit();
                System.out.println("Commit: " + step.getSagaId() + " success.");
            } catch (Exception e) {
                System.out.println("Commit: " + step.getSagaId() + " failed.");
                compensatingChain(i);
                break;
            }
        }
    }


    @SuppressWarnings("unchecked")
    public <T> SagaStep<T> getSaga(String sagaId, Class<T> type) {
        SagaStep<?> step = steps.stream()
                .filter(item -> item.getSagaId().equals(sagaId))
                .findFirst()
                .orElse(null);

        if (step != null && type.isInstance(step.getResult())) {
            return (SagaStep<T>) step;
        }
        return null;
    }

    public void compensatingChain(int index){
        System.out.println("----------Compensation chain started----------");
        List<SagaStep<?>> compensatingSteps =  steps.reversed();
        steps.removeLast();
        for (int i = 0; i <= index - 1; i++) {
            SagaStep<?> step = compensatingSteps.get(i);
            try{
                System.out.println("Compensate: " +step.getSagaId());
                compensatingSteps.get(i).compensate();
            }catch (Exception e){
                System.out.println(step.getSagaId() + " compensating error!!");
            }
        }
    }

}
