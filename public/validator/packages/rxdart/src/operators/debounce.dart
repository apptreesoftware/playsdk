library rx.operators.debounce;

import 'package:rxdart/src/observable/stream.dart';

class DebounceObservable<T> extends StreamObservable<T> {

  Timer _timer;
  final Duration _duration;
  bool _closeAfterNextEvent = false;

  DebounceObservable(Stream<T> stream, Duration duration) : _duration = duration {
    controller = new StreamController<T>(sync: true,
        onListen: () {
          subscription = stream.listen(_resetTimer,
          onError: (e, s) => throwError(e, s),
          onDone: () {
            _closeAfterNextEvent = true;
          });
        },
        onCancel: () => subscription.cancel());

    setStream(stream.isBroadcast ? controller.stream.asBroadcastStream() : controller.stream);
  }

  void _resetTimer(T value) {
    if (_timer != null && _timer.isActive) _timer.cancel();

    if (hasListener()) {
      _timer = Zone.ROOT.createTimer(_duration, () {
        controller.add(value);

        if (_closeAfterNextEvent) controller.close();
      });
    }
  }

}