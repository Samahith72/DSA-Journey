# WIN #2 — Best Time to Buy and Sell Stock

## Problem

You are given an array where `prices[i]` represents the price of a stock on the `i`th day.

You need to choose one day to buy the stock and a later day to sell it in order to get the maximum possible profit.

If no profit can be made, return `0`.

---

## My Java Solution

```java
class Solution {
    public int maxProfit(int[] prices) {

        int result = 0;
        int minimum = Integer.MAX_VALUE;

        for (int i = 0; i < prices.length; i++) {

            if (minimum > prices[i]) {
                minimum = prices[i];
            }

            if (result < prices[i] - minimum) {
                result = prices[i] - minimum;
            }
        }

        return result;
    }
}
```

---

## My Thought Process

At first, the problem looks like we need to compare every buying day with every selling day. However, doing that would require checking many combinations.

Instead, I realized that while moving through the array, I only need to remember the lowest stock price I have seen so far.

For every new price:

1. Check if it is lower than the minimum price seen so far.
2. If it is lower, update the minimum price.
3. Calculate the profit if I sell on the current day.
4. Compare that profit with the maximum profit found so far.
5. Keep the larger profit.

For example:

```text
prices = [7, 1, 5, 3, 6, 4]
```

While moving through the array:

```text
7 → minimum price = 7
1 → new minimum price = 1
5 → possible profit = 5 - 1 = 4
3 → possible profit = 3 - 1 = 2
6 → possible profit = 6 - 1 = 5
4 → possible profit = 4 - 1 = 3
```

The maximum profit found is `5`.

The important part is that the minimum price always comes from a previous day, so buying always happens before selling.

---

## Pattern Recognition

### Pattern: One-Pass Minimum Tracking

This pattern is useful when:

* I need to find the best result while scanning an array.
* The current answer depends on the best or worst value seen previously.
* I do not need to compare every possible pair.

The general approach is:

```text
Initialize the minimum value.

For every element:
    Update the minimum value if necessary.
    Calculate the result using the current element.
    Update the maximum result.
```

For this problem:

```text
minimum = lowest buying price seen so far

profit = current price - minimum

result = maximum profit seen so far
```

---

## Complexity

### Time Complexity

```text
O(n)
```

The array is traversed only once.

### Space Complexity

```text
O(1)
```

Only two extra variables are used regardless of the input size.

---

## Key Learning

I learned that I do not always need to compare every possible pair in an array.

Sometimes, I can solve the problem by keeping track of useful information from previous elements.

In this problem, the useful information is the minimum stock price seen so far.

For every new price, I ask:

```text
If I sell today, what would my profit be if I had bought at the cheapest price before today?
```

This reduces the solution from a brute-force `O(n²)` approach to an `O(n)` solution.
