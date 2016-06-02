library rx.operators.sample;

import 'package:rxdart/src/observable/stream.dart';

class SampleObservable<T> extends StreamObservable<T> {

  SampleObservable(Stream<T> stream, Stream sampleStream) {
    StreamSubscription sampleSubscription;
    T currentValue;

    controller = new StreamController<T>(sync: true,
        onListen: () {
          subscription = stream.listen((T value) {
            if (hasListener()) currentValue = value;
          },
          onError: throwError);

          sampleSubscription = sampleStream.listen((_) {
            if (currentValue != null && hasListener()) controller.add(currentValue);
          },
          onError: throwError,
          onDone: controller.close);
        },
        onCancel: () {
          return Future.wait([
            subscription.cancel(),
            sampleSubscription.cancel()
          ].where((Future cancelFuture) => cancelFuture != null));
        });

    setStream(stream.isBroadcast ? controller.stream.asBroadcastStream() : controller.stream);
  }

}