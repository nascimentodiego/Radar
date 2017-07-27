package br.com.dfn.radar.model.communication.api.observable;

import io.reactivex.Scheduler;

public interface SchedulerContracts {

    Scheduler io();

    Scheduler mainThread();

}
