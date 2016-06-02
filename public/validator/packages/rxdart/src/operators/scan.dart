library rx.operators.scan;

import 'package:rxdart/src/observable/stream.dart';

class ScanObservable<T, S> extends StreamObservable<S> {

  ScanObservable(Stream<T> stream, S predicate(S accumulated, T value, int index), [S seed]) {
    int index = 0;
    S acc = seed;

    controller = new StreamController<S>(sync: true,
        onListen: () {
          subscription = stream.listen((T value) {
            if (hasListener()) {
              try {
                acc = predicate(acc, value, index++);

                controller.add(acc);
              } catch (error) {
                controller.addError(error, error.stackTrace);
              }
            }
          },
          onError: controller.addError,
          onDone: controller.close);
        },
        onCancel: () => subscription.cancel());

    setStream(stream.isBroadcast ? controller.stream.asBroadcastStream() : controller.stream);
  }

}