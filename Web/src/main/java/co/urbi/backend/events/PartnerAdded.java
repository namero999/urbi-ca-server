package co.urbi.backend.events;

import co.urbi.contracts.Registry.WhitelistedAddedEventResponse;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Log4j2
public class PartnerAdded implements Subscriber<WhitelistedAddedEventResponse> {

    @Override
    public void onSubscribe(Subscription s) {
    }

    @Override
    public void onNext(WhitelistedAddedEventResponse response) {
        log.info(response);
    }

    @Override
    public void onError(Throwable t) {
        log.error(t);
    }

    @Override
    public void onComplete() {
        log.info("Event completed");
    }

}