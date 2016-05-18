library rx.operators.tap;

import 'package:rxdart/src/observable/stream.dart';

class TapObservable<T> extends StreamObservable<T> with ControllerMixin<T> {

  TapObservable(Stream<T> stream, void action(T value)) {
    controller = new StreamController<T>(sync: true,
        onListen: () {
          subscription = stream.listen((T value) {
            if (hasListener()) {
              action(value);

              controller.add(value);
            }
          },
          onError: (e, s) => throwError(e, s),
          onDone: controller.close);
        },
        onCancel: () => subscription.cancel());

    setStream(stream.isBroadcast ? controller.stream.asBroadcastStream() : controller.stream);
  }

}