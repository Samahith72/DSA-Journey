
# WIN #150 — 875. Koko Eating Bananas

[LeetCode Problem](https://leetcode.com/problems/koko-eating-bananas/)

**Difficulty:** Medium  
**Topics:** Array, Binary Search

---

## Problem Statement

Koko has `piles` of bananas and `h` hours to eat them.

She chooses an integer eating speed `k` (bananas per hour). Each hour, she chooses one pile and eats up to `k` bananas from it. If the pile has fewer than `k` bananas, she eats the entire pile and does not eat from another pile during that hour.

Return the minimum integer eating speed `k` that allows Koko to eat all the bananas within `h` hours.

### Example

```text
Input: piles = [3, 6, 7, 11], h = 8
Output: 4
```

At a speed of `4` bananas per hour, Koko finishes the piles in `1 + 2 + 2 + 3 = 8` hours.

---

## Core Idea

Use binary search on the possible eating speeds.

- The minimum possible speed is `1`.
- The maximum required speed is the largest pile, because Koko can finish any pile in one hour at that speed.
- For a candidate speed `k`, calculate the total hours needed to finish every pile.
- If the required hours are at most `h`, `k` is fast enough, so try a smaller speed.
- Otherwise, increase the speed.

The answer is the smallest speed that satisfies the hour limit.

---

## Java Solution

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 0;

        for (int pile : piles) {
            high = Math.max(high, pile);
        }

        while (low < high) {
            int k = low + (high - low) / 2;
            int hours = 0;

            for (int pile : piles) {
                hours += (pile + k - 1) / k;
            }

            if (hours <= h) {
                high = k;
            } else {
                low = k + 1;
            }
        }

        return low;
    }
}
```

---

## Step-by-Step Explanation

1. Initialize `low = 1` and find the largest pile to use as `high`.
2. While `low < high`, calculate the middle speed `k`.
3. For each pile, calculate the hours needed using ceiling division:

   ```text
   (pile + k - 1) / k
   ```

4. Add the hours for all piles.
5. If the total hours are within `h`, the speed is feasible. Set `high = k` to search for a potentially smaller speed.
6. If the total hours exceed `h`, the speed is too slow. Set `low = k + 1`.
7. When `low == high`, return that speed.

**Implementation note:** The submitted code uses `int hours`. The total hours can exceed the range of an `int` under the problem's constraints, so use `long hours` and cast the ceiling-division result to `long` in a robust implementation.

---

## Dry Run

```text
piles = [3, 6, 7, 11]
h = 8
```

The search range is `low = 1`, `high = 11`.

| low | high | k | Hours needed | Action |
|---:|---:|---:|---:|---|
| 1 | 11 | 6 | 6 | Feasible; set `high = 6` |
| 1 | 6 | 3 | 10 | Too slow; set `low = 4` |
| 4 | 6 | 5 | 8 | Feasible; set `high = 5` |
| 4 | 5 | 4 | 8 | Feasible; set `high = 4` |

Now `low == high == 4`, so the minimum eating speed is `4`.

---

## Edge Cases

- **Only one pile:** The minimum speed is the ceiling of `pile / h`, subject to the allowed hours.
- **Speed equals the largest pile:** Koko finishes each pile in one hour.
- **Many small piles:** Each pile still requires at least one hour, even if it contains fewer than `k` bananas.
- **Large piles or many piles:** Use `long` for the accumulated hours to avoid integer overflow.

---

## Complexity Analysis

Let `n` be the number of piles and `m` be the size of the largest pile.

- **Time Complexity:** `O(n log m)` — binary search checks at most `O(log m)` speeds, and each check scans all piles.
- **Space Complexity:** `O(1)` — the algorithm uses a fixed number of variables.

---

## Pattern Recognition

This is a **binary search on the answer** problem.

When a problem asks for the minimum value that satisfies a condition, check whether a candidate value is feasible. Here, increasing the eating speed can only reduce or preserve the number of hours required, so feasibility is monotonic.

---

## Key Takeaway

Binary search can find the minimum feasible eating speed by repeatedly checking how many hours a candidate speed requires. Keep the feasible half of the search range until only the smallest valid speed remains.
