#!/bin/sh
set -eu
cd "$(dirname "$0")"
if [ "$#" -gt 1 ]; then
  echo 'Usage: sh test.sh [1|2|3|all]' >&2
  exit 2
fi
case "${1:-all}" in
  1|2|3) groups="$1" ;;
  all) groups='1 2 3' ;;
  *) echo 'Usage: sh test.sh [1|2|3|all]' >&2; exit 2 ;;
esac
for part in $groups; do
  set --
  for source in ./*.java; do
    case "$source" in
      ./Test1.java|./Test2.java|./Test3.java|./TestSupport.java) ;;
      *) set -- "$@" "$source" ;;
    esac
  done
  build="out/part$part"
  mkdir -p "$build"
  javac -Xlint:rawtypes -Xlint:unchecked -Werror -d "$build" \
    "$@" TestSupport.java "Test$part.java"
  java -cp "$build" "Test$part"
done
