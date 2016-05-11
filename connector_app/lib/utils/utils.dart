library connector_app.utils;

import 'dart:async';

import 'package:rxdart/rxdart.dart' as rx;
import 'package:autonotify_observe/autonotify_observe.dart';
import 'package:polymer_autonotify/polymer_autonotify.dart';

abstract class ObservableHelpers extends Observable {

  rx.Observable<PropertyChangeRecord> get _propertyChanges =>
      new rx.Observable.fromStream(this.changes)
          .flatMap((List<ChangeRecord> changes) =>
      new rx.Observable.fromIterable(changes))
          .where((ChangeRecord record) => record is PropertyChangeRecord);

  rx.Observable<PropertyChangeRecord> propertyChangesOfType(String type) =>
      _propertyChanges.where((record) => record.name == type);

  Set<StreamSubscription> subscriptions = new Set();

  void detached() {
    subscriptions.forEach((s) => s.cancel());
  }
}
