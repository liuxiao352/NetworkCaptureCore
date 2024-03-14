package com.liuxiao352.networkcapturecore.entity;

public class KeyValue {

  private final CharSequence key;
  private final CharSequence value;

  public KeyValue( CharSequence key, CharSequence value) {
    this.key = key;
    this.value = value;
  }

  public CharSequence getKey() {
    return key;
  }

  public CharSequence getValue() {
    return value;
  }
}
