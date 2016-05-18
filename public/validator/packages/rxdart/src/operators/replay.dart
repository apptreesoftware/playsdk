library rx.operators.replay;

import 'dart:collection';

import 'package:rxdart/src/observable/stream.dart';

class ReplayObservable<T> extends StreamObservable<T> {

  Queue<T> buffer = new Queue<T>();
  int gobalDataIndex = 0;
  final bool _completeWhenBufferExhausted;

  ReplayObservable(Stream<T> stream, {int bufferSize: 0, bool completeWhenBufferExhausted: false}) : _completeWhenBufferExhausted = completeWhenBufferExhausted {
    controller = new StreamController<T>(sync: true,
        onListen: () {
          subscription = stream.listen((T value) {
            if (hasListener()) {
              buffer.add(value);
              gobalDataIndex++;

              if (bufferSize > 0 && buffer.length > bufferSize) buffer
                  .removeFirst();

              controller.add(value);
            }
          },
          onError: (e, s) => throwError(e, s)/*,
           onDone:  controller.close */ /* should not close, since it can be replayed at any later point */);
        },
        onCancel: () => subscription.cancel());

    setStream(stream.isBroadcast ? controller.stream.asBroadcastStream() : controller.stream);
  }

  @override
  StreamSubscription<T> listen(void onData(T event),
      { Function onError,
      void onDone(),
      bool cancelOnError }) {
    int localDataIndex = 0;

    final StreamSubscription<T> subscription = stream.listen((T data) {
      if (localDataIndex < gobalDataIndex) onData(data);

      localDataIndex++;
    }, onError: onError, onDone: onDone, cancelOnError: cancelOnError);

    buffer.forEach(controller.add);

    if (_completeWhenBufferExhausted) controller.close();

    gobalDataIndex += buffer.length;

    return subscription;
  }

}