library rx.observable.stream;

import 'dart:async';

import 'package:rxdart/src/observable/stream_subscription.dart' show ForwardingStreamSubscription;

import 'package:rxdart/src/observable.dart' show Observable;
import 'package:rxdart/src/operators/debounce.dart' show DebounceObservable;
import 'package:rxdart/src/operators/retry.dart' show RetryObservable;
import 'package:rxdart/src/operators/throttle.dart' show ThrottleObservable;
import 'package:rxdart/src/operators/buffer_with_count.dart' show BufferWithCountObservable;
import 'package:rxdart/src/operators/window_with_count.dart' show WindowWithCountObservable;
import 'package:rxdart/src/operators/flat_map.dart' show FlatMapObservable;
import 'package:rxdart/src/operators/flat_map_latest.dart' show FlatMapLatestObservable;
import 'package:rxdart/src/operators/take_until.dart' show TakeUntilObservable;
import 'package:rxdart/src/operators/scan.dart' show ScanObservable;
import 'package:rxdart/src/operators/tap.dart' show TapObservable;
import 'package:rxdart/src/operators/start_with.dart' show StartWithObservable;
import 'package:rxdart/src/operators/repeat.dart' show RepeatObservable;
import 'package:rxdart/src/operators/replay.dart' show ReplayObservable;
import 'package:rxdart/src/operators/min.dart' show MinObservable;
import 'package:rxdart/src/operators/max.dart' show MaxObservable;
import 'package:rxdart/src/operators/interval.dart' show IntervalObservable;
import 'package:rxdart/src/operators/sample.dart' show SampleObservable;
import 'package:rxdart/src/operators/time_interval.dart' show TimeIntervalObservable, TimeInterval;
import 'package:rxdart/src/operators/pluck.dart' show PluckObservable;
import 'package:rxdart/src/operators/reverse.dart' show ReverseObservable;

export 'dart:async';

class StreamObservable<T> implements Observable<T> {

  StreamController<T> controller;
  StreamSubscription subscription;

  void throwError(e, s) => controller.addError(e, s);

  Stream<T> stream;

  final List<ForwardingStreamSubscription<T>> _subscriptions = <ForwardingStreamSubscription<T>>[];

  @override
  bool get isBroadcast {
    return (stream != null) ? stream.isBroadcast : false;
  }

  StreamObservable();

  void setStream(Stream<T> stream) {
    this.stream = stream;
  }

  bool hasListener() {
    if (_subscriptions.isEmpty) return true;
    
    final ForwardingStreamSubscription<T> activeSubscription = _subscriptions.firstWhere((ForwardingStreamSubscription<T> subscription) => !subscription.isCanceled, orElse: () => null);

    return activeSubscription != null;
  }

  StreamSubscription<T> listen(void onData(T event),
    { Function onError,
      void onDone(),
      bool cancelOnError }) {
        final ForwardingStreamSubscription<T> subscription = new ForwardingStreamSubscription<T>(
          stream.listen(onData, onError: onError, onDone: onDone, cancelOnError: cancelOnError)
        );

        _subscriptions.add(subscription);

        return subscription;
  }

  Future cancelSubscription() => controller.close();

  Observable<T> asBroadcastStream({
    void onListen(StreamSubscription<T> subscription),
    void onCancel(StreamSubscription<T> subscription) }) => new StreamObservable()..setStream(stream.asBroadcastStream(onListen: onListen, onCancel: onCancel));

  Observable/*<S>*/ map/*<S>*/(/*=S*/ convert(T event)) => new StreamObservable()..setStream(stream.map(convert));

  Observable asyncMap(dynamic convert(T value)) => new StreamObservable()..setStream(stream.asyncMap(convert));

  Observable<T> where(bool test(T event)) => new StreamObservable<T>()..setStream(stream.where(test));

  Observable expand(Iterable convert(T value)) => new StreamObservable()..setStream(stream.expand(convert));

  Observable asyncExpand(Stream convert(T value)) => new StreamObservable()..setStream(stream.asyncExpand(convert));

  Observable<T> distinct([bool equals(T previous, T next)]) => new StreamObservable<T>()..setStream(stream.distinct(equals));

  Observable<T> handleError(Function onError, { bool test(error) }) => new StreamObservable<T>()..setStream(stream.handleError(onError, test: test));

  Observable<T> skip(int count) => new StreamObservable<T>()..setStream(stream.skip(count));

  Observable<T> skipWhile(bool test(T element)) => new StreamObservable<T>()..setStream(stream.skipWhile(test));

  Observable<T> take(int count) => new StreamObservable<T>()..setStream(stream.take(count));

  Observable<T> takeWhile(bool test(T element)) => new StreamObservable<T>()..setStream(stream.takeWhile(test));

  Observable<T> timeout(Duration timeLimit, {void onTimeout(EventSink sink)}) => new StreamObservable<T>()..setStream(stream.timeout(timeLimit, onTimeout: onTimeout));

  Observable<T> retry([int count]) => new RetryObservable<T>(stream, count);

  Observable<T> debounce(Duration duration) => new DebounceObservable<T>(stream, duration);

  Observable<T> throttle(Duration duration) => new ThrottleObservable<T>(stream, duration);

  Observable<List<T>> bufferWithCount(int count, [int skip]) => new BufferWithCountObservable<T, List<T>>(stream, count, skip);

  Observable<Observable<T>> windowWithCount(int count, [int skip]) => new WindowWithCountObservable<T, StreamObservable<T>>(stream, count, skip);

  Observable/*<S>*/ flatMap(Stream/*<S>*/ predicate(T value)) => new FlatMapObservable<T, dynamic/*=S*/>(stream, predicate);

  Observable/*<S>*/ flatMapLatest(Stream/*<S>*/ predicate(T value)) => new FlatMapLatestObservable<T, dynamic/*=S*/>(stream, predicate);

  Observable<T> takeUntil(Stream<dynamic> otherStream) => new TakeUntilObservable<T, dynamic>(stream, otherStream);

  Observable/*<S>*/ scan(dynamic/*<S>*/ predicate(dynamic/*<S>*/ accumulated, T value, int index), [dynamic/*<S>*/ seed]) => new ScanObservable<T, dynamic/*=S*/>(stream, predicate, seed);

  Observable<T> tap(void action(T value)) => new TapObservable<T>(stream, action);

  Observable<T> startWith(List<T> startValues) => new StartWithObservable<T>(stream, startValues);

  Observable<T> repeat(int repeatCount) => new RepeatObservable<T>(stream, repeatCount);

  Observable<T> replay({int bufferSize: 0, bool completeWhenBufferExhausted: false}) => new ReplayObservable<T>(stream, bufferSize: bufferSize, completeWhenBufferExhausted: completeWhenBufferExhausted);

  Observable<T> min([int compare(T a, T b)]) => new MinObservable<T>(stream, compare);

  Observable<T> max([int compare(T a, T b)]) => new MaxObservable<T>(stream, compare);

  Observable<T> interval(Duration duration) => new IntervalObservable<T>(stream, duration);

  Observable<T> sample(Stream sampleStream) => new SampleObservable<T>(stream, sampleStream);

  Observable<TimeInterval<T>> timeInterval() => new TimeIntervalObservable<T, TimeInterval<T>>(stream);

  Observable/*<S>*/ pluck(List<dynamic> sequence, {bool throwOnNull: false}) => new PluckObservable<T, dynamic/*=S*/>(stream, sequence, throwOnNull: throwOnNull);

  Observable<T> reverse() => new ReverseObservable(stream.asBroadcastStream());

  Future<bool> any(bool test(T element)) => stream.any(test);

  Future<bool> contains(Object needle) => stream.contains(needle);

  Future drain([var futureValue]) => stream.drain(futureValue);

  Future<T> elementAt(int index) => stream.elementAt(index);

  Future<bool> every(bool test(T element)) => stream.every(test);

  Future<dynamic> firstWhere(bool test(T element), {Object defaultValue()}) => stream.firstWhere(test, defaultValue: defaultValue);

  Future forEach(void action(T element)) => stream.forEach(action);

  Future<String> join([String separator = ""]) => stream.join(separator);

  Future<dynamic> lastWhere(bool test(T element), {Object defaultValue()}) => stream.lastWhere(test, defaultValue: defaultValue);

  Future pipe(StreamConsumer<T> streamConsumer) => stream.pipe(streamConsumer);

  Future/*<S>*/ fold/*<S>*/(var/*=S*/ initialValue,
      /*=S*/ combine(var/*=S*/ previous, T element)) => stream.fold(initialValue, combine);

  Future<T> reduce(T combine(T previous, T element)) => stream.reduce(combine);

  Future<T> singleWhere(bool test(T element)) => stream.singleWhere(test);

  Future<List<T>> toList() => stream.toList();

  Future<Set<T>> toSet() => stream.toSet();

  Stream transform(StreamTransformer<T, dynamic> streamTransformer) => stream.transform(streamTransformer);

  Future<T> get first => stream.first;

  Future<T> get last => stream.last;

  Future<T> get single => stream.single;

  Future<bool> get isEmpty => stream.isEmpty;

  Future<int> get length => stream.length;
}

abstract class ControllerMixin<T> {



}