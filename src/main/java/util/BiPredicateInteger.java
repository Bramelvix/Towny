package util;

import java.util.Objects;

@FunctionalInterface
public interface BiPredicateInteger {
	boolean test(int t, int u);

	default BiPredicateInteger and(BiPredicateInteger other) {
		Objects.requireNonNull(other);
		return (int t, int u) -> test(t, u) && other.test(t, u);
	}

	default BiPredicateInteger negate() {
		return (int t, int u) -> !test(t, u);
	}

	default BiPredicateInteger or(BiPredicateInteger other) {
		Objects.requireNonNull(other);
		return (int t, int u) -> test(t, u) || other.test(t, u);
	}
}
