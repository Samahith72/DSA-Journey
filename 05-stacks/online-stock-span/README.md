# WIN #113 — 901. Online Stock Span

**LeetCode:** [901. Online Stock Span](https://leetcode.com/problems/online-stock-span/)  
**Difficulty:** Medium  
**Topics:** Stack, Design, Monotonic Stack, Data Stream

---

## Problem Statement

Design a class called `StockSpanner` that calculates the **stock span** for each day's stock price.

The stock span of a day is the maximum number of consecutive days, ending on the current day, for which the stock price was less than or equal to the current day's price.

In other words, starting from the current day and moving backward, count consecutive days whose prices are less than or equal to the current price. Stop when you encounter a previous price that is strictly greater.

### Example

**Input:**
```text
StockSpanner stockSpanner = new StockSpanner();

stockSpanner.next(100);
stockSpanner.next(80);
stockSpanner.next(60);
stockSpanner.next(70);
stockSpanner.next(60);
stockSpanner.next(75);
stockSpanner.next(85);
```

**Output:**
```text
[1, 1, 1, 2, 1, 4, 6]
```

### Explanation

| Day | Price | Stock Span | Explanation |
|---:|---:|---:|---|
| 1 | 100 | 1 | No previous day exists. |
| 2 | 80 | 1 | The previous price, `100`, is greater than `80`. |
| 3 | 60 | 1 | The previous price, `80`, is greater than `60`. |
| 4 | 70 | 2 | `60` and `70` are both less than or equal to `70`. |
| 5 | 60 | 1 | The previous price, `70`, is greater than `60`. |
| 6 | 75 | 4 | The prices `60`, `70`, `60`, and `75` are all less than or equal to `75`. |
| 7 | 85 | 6 | The previous prices `75`, `60`, `70`, `60`, and `80` are all less than or equal to `85`. The earlier price `100` is greater. |

---

## Core Idea

The problem asks us to calculate the stock span whenever a new price arrives.

A straightforward solution would examine previous prices one by one for every new price. However, repeatedly scanning previous days can take O(n²) time over a sequence of `n` prices.

Your solution uses a **monotonic stack** to calculate spans efficiently.

Instead of storing only individual prices, it stores pairs:

```text
[price, span]
```

Each pair represents a previous price and the span already calculated for that price.

### Main Strategy

1. Initialize an empty stack.
2. When a new price arrives, start its span at `1`, accounting for the current day.
3. While the stack is not empty and its top price is less than or equal to the current price:
   - Remove the top entry.
   - Add its previously calculated span to the current span.
4. Push the current price and its calculated span onto the stack.
5. Return the span.

### Why Store the Previous Span?

Suppose the stack contains:

```text
[70, 2]
```

This means the price `70` has a span of `2`.

If the next price is `75`, both the previous price `70` and the two-day range represented by its span can be included in the new span.

Instead of examining those days individually, your algorithm adds the stored span directly.

This is the key optimization in your implementation.

---

## Java Solution

```java
class StockSpanner {

    private Stack<int[]> stack;

    public StockSpanner() {
        stack = new Stack<>();
    }

    public int next(int price) {
        int span = 1;

        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }

        stack.push(new int[]{price, span});
        return span;
    }
}
```

---

## Step-by-Step Explanation

### 1. Declare the Stack

```java
private Stack<int[]> stack;
```

The stack stores integer arrays containing two values:

```text
[price, span]
```

The first value is the stock price, and the second is its calculated span.

For example:

```text
[70, 2]
```

means that the stock price was `70` and its span was `2`.

Using an array allows your implementation to store both values in a single stack entry.

### 2. Initialize the Stack

```java
public StockSpanner() {
    stack = new Stack<>();
}
```

The constructor creates an empty stack when a `StockSpanner` object is instantiated.

As new prices arrive through calls to `next()`, the stack maintains the information required to calculate subsequent spans.

### 3. Initialize the Current Span

```java
int span = 1;
```

Every price has a minimum span of `1`, because the current day always counts toward its own stock span.

For example, if today's price is `60` and yesterday's price is `80`, today's span is `1`.

The previous price is greater, so the consecutive range cannot extend further backward.

### 4. Remove Previous Prices That Are Less Than or Equal to the Current Price

```java
while (!stack.isEmpty() && stack.peek()[0] <= price) {
    span += stack.pop()[1];
}
```

This is the most important part of the solution.

The condition:

```java
stack.peek()[0] <= price
```

compares the previous price at the top of the stack with the new price.

If the previous price is less than or equal to the current price, that previous entry's entire span can be included in the current span.

The algorithm therefore:

1. Removes the previous entry.
2. Retrieves its stored span using `stack.pop()[1]`.
3. Adds that span to the current span.
4. Continues checking earlier entries.

The loop stops when the stack becomes empty or the top price is greater than the current price.

That greater price is the boundary preventing the current span from extending further backward.

### 5. Push the Current Price and Span

```java
stack.push(new int[]{price, span});
```

After calculating the span, store both the current price and its span as a new stack entry.

For example, if the current price is `75` and its span is `4`, the entry is:

```text
[75, 4]
```

This information can be reused when calculating the span for future prices.

### 6. Return the Span

```java
return span;
```

The method returns the calculated span for the current stock price.

The same `StockSpanner` object can process subsequent prices because the stack preserves the necessary state between calls.

---

## Dry Run

Consider these stock prices:

```text
100, 80, 60, 70, 60, 75, 85
```

The stack entries below are shown from bottom to top.

| Day | Price | Span calculation | Stack after processing | Returned span |
|---:|---:|---|---|---:|
| 1 | 100 | Start with `1` | `[(100, 1)]` | 1 |
| 2 | 80 | `100 > 80`, so no entry is removed | `[(100, 1), (80, 1)]` | 1 |
| 3 | 60 | `80 > 60`, so no entry is removed | `[(100, 1), (80, 1), (60, 1)]` | 1 |
| 4 | 70 | Remove `(60, 1)`; span becomes `1 + 1 = 2` | `[(100, 1), (80, 1), (70, 2)]` | 2 |
| 5 | 60 | `70 > 60`, so no entry is removed | `[(100, 1), (80, 1), (70, 2), (60, 1)]` | 1 |
| 6 | 75 | Remove `(60, 1)`, then `(70, 2)`; span becomes `1 + 1 + 2 = 4` | `[(100, 1), (80, 1), (75, 4)]` | 4 |
| 7 | 85 | Remove `(75, 4)` and `(80, 1)`; span becomes `1 + 4 + 1 = 6` | `[(100, 1), (85, 6)]` | 6 |

### Understanding Day 6: Price 75

Before processing `75`, the stack contains:

```text
[(100, 1), (80, 1), (70, 2), (60, 1)]
```

Start with:

```text
span = 1
```

The top entry is `(60, 1)`.

Since `60 <= 75`:

```text
span = 1 + 1 = 2
```

The next entry is `(70, 2)`.

Since `70 <= 75`:

```text
span = 2 + 2 = 4
```

The next price is `80`, which is greater than `75`.

The loop stops.

The final span is `4`, and the algorithm pushes:

```text
(75, 4)
```

The important point is that the algorithm adds the stored span `2` for price `70`, rather than processing the two days represented by that span individually.

### Understanding Day 7: Price 85

The stack is:

```text
[(100, 1), (80, 1), (75, 4)]
```

Start with:

```text
span = 1
```

Since `75 <= 85`, remove `(75, 4)`:

```text
span = 1 + 4 = 5
```

Now `80 <= 85`, so remove `(80, 1)`:

```text
span = 5 + 1 = 6
```

The remaining price is `100`, which is greater than `85`.

The loop stops, and the method returns:

```text
6
```

The final output for the sequence is:

```text
[1, 1, 1, 2, 1, 4, 6]
```

---

## Edge Cases

| Case | Prices | Expected spans | Explanation |
|---|---|---|---|
| First price | `100` | `1` | No previous prices exist. |
| Strictly decreasing prices | `100, 90, 80` | `1, 1, 1` | Every new price is lower than the previous price. |
| Strictly increasing prices | `10, 20, 30` | `1, 2, 3` | Each new price includes every previous day in its span. |
| Equal prices | `50, 50, 50` | `1, 2, 3` | Equal prices are included because the condition uses `<=`. |
| Large increase after decreasing prices | `100, 80, 60, 90` | `1, 1, 1, 3` | The price `90` absorbs the spans for `60` and `80`. |
| New price below the previous maximum | `100, 70, 85` | `1, 1, 2` | The price `100` remains the boundary for `85`. |

---

## Complexity Analysis

Let `n` be the number of prices processed.

### Time Complexity: O(1) Amortized per Call

A single call to `next()` may pop several entries from the stack.

However, each entry is pushed onto the stack once and popped at most once.

Across `n` calls, the total number of stack operations is O(n).

Therefore:

- **Amortized time per call:** O(1)
- **Total time for n calls:** O(n)

### Space Complexity: O(n)

In the worst case, prices may arrive in strictly decreasing order. Each new price is smaller than the previous one, so no entries are removed.

The stack can therefore contain all `n` price-span pairs.

**Space Complexity: O(n)**

---

## Pattern Recognition

**Pattern: Monotonic Stack with Span Compression**

This problem combines a monotonic stack with previously calculated spans.

Instead of storing every previous day's price, the stack stores price-span pairs that summarize consecutive ranges.

Recognize this approach when:

- Data arrives one element at a time.
- Each new element depends on previous elements.
- You need to find a consecutive range satisfying a comparison condition.
- Previously calculated ranges can be reused to avoid repeated work.

### Recognition Rule

For each new price:

```text
Start span at 1.

While the stack is not empty and the top price <= current price:
    Add the top entry's span to the current span.
    Remove the top entry.

Push [current price, current span].
Return current span.
```

The stack maintains decreasing prices from bottom to top, because any previous price less than or equal to a newly processed price is removed.

This lets the algorithm quickly find the nearest previous price that is strictly greater than the current price.

---

## Key Takeaways

1. Each stack entry stores a stock price and its previously calculated span.
2. Every new price begins with a span of `1`.
3. Previous prices less than or equal to the current price are removed from the stack.
4. Their stored spans are added to the current span, avoiding repeated calculations.
5. The remaining top price, if one exists, is strictly greater than the current price and marks the end of the span.
6. Each entry is pushed once and popped at most once, giving O(1) amortized time per call.
7. The stack preserves state between calls, making it suitable for processing a data stream.

**Final Mental Model:** Start with the current day. Remove previous prices that are less than or equal to today's price, adding their stored spans as you go. Stop at the first greater price, store today's price and its span, and return the result.