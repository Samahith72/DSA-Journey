# WIN #105 — 1475. Final Prices With a Special Discount in a Shop

**LeetCode:** [1475. Final Prices With a Special Discount in a Shop](https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop/)  
**Difficulty:** Easy  
**Topics:** Array, Stack, Monotonic Stack

---

## 1. Problem

You are given an integer array `prices`, where `prices[i]` represents the price of the `i`th item.

For each item, find the first price to its right that is less than or equal to its current price. That price becomes the discount.

The final price is calculated as:

`finalPrice[i] = prices[i] - discount`

If no such price exists, the item receives no discount, and its final price remains unchanged.

Return the array containing the final prices.

### Example

**Input:**
```text
prices = [8, 4, 6, 2, 3]
```

**Output:**
```text
[4, 2, 4, 2, 3]
```

**Explanation:**

- Price `8` gets a discount of `4`, so its final price is `4`.
- Price `4` gets a discount of `2`, so its final price is `2`.
- Price `6` gets a discount of `2`, so its final price is `4`.
- Price `2` has no qualifying price to its right, so it remains `2`.
- Price `3` has no price to its right, so it remains `3`.

---

## 2. Core Idea

Use a **monotonic stack** to find the next smaller or equal price.

Instead of checking every price to the right of each item, process the array **from right to left**.

The stack stores prices that may serve as discounts for items to their left.

For each price:

1. Remove stack elements that are greater than the current price.
2. If the stack is not empty, its top is the nearest price to the right that is less than or equal to the current price.
3. Subtract that price from the current price.
4. If the stack is empty, there is no discount.
5. Push the current price onto the stack so it can be considered for earlier items.

The key condition is:

```java
while (!stack.isEmpty() && stack.peek() > prices[i]) {
    stack.pop();
}
```

We remove prices that are too large to serve as a discount for the current item. Prices equal to the current price are retained because the problem allows a discount that is less than or equal to the current price.

---

## 3. Java Solution

```java
class Solution {

    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[n];

        ans[n - 1] = prices[n - 1];
        stack.push(prices[n - 1]);

        for (int i = n - 2; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() > prices[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                ans[i] = prices[i] - stack.peek();
            }
            else {
                ans[i] = prices[i];
            }

            stack.push(prices[i]);
        }

        return ans;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the variables

```java
int n = prices.length;
Stack<Integer> stack = new Stack<>();
int[] ans = new int[n];
```

- `n` stores the number of items.
- `stack` stores candidate discount prices.
- `ans` stores the final price of every item.

### Step 2: Handle the last item

```java
ans[n - 1] = prices[n - 1];
stack.push(prices[n - 1]);
```

The last item has no price to its right, so it cannot receive a discount.

Its final price remains unchanged.

We also push its price onto the stack because it may provide a discount to an earlier item.

For the example:

```text
prices = [8, 4, 6, 2, 3]
```

Initially:

```text
ans   = [_, _, _, _, 3]
stack = [3]
```

### Step 3: Traverse from right to left

```java
for (int i = n - 2; i >= 0; i--)
```

We start at the second-last item and move toward the first item.

Why right to left?

Because when processing an item, we need information about the prices to its right. By traversing backward, we have already processed those prices and can use the stack to find a suitable discount.

### Step 4: Remove prices that are too large

```java
while (!stack.isEmpty() && stack.peek() > prices[i]) {
    stack.pop();
}
```

The top of the stack must be less than or equal to the current price to qualify as a discount.

If the top is greater, remove it.

For example, suppose the current price is `2` and the stack contains:

```text
Top -> 3
       6
```

Both prices are greater than `2`, so neither can provide a discount for this item. They are removed until a suitable candidate is found or the stack becomes empty.

### Step 5: Calculate the final price

```java
if (!stack.isEmpty()) {
    ans[i] = prices[i] - stack.peek();
}
else {
    ans[i] = prices[i];
}
```

There are two possibilities.

**Case 1: The stack is not empty.**

Its top is the nearest qualifying price to the right, so subtract it from the current price.

**Case 2: The stack is empty.**

No qualifying price exists, so the current item receives no discount.

### Step 6: Push the current price

```java
stack.push(prices[i]);
```

After calculating the answer, push the current price onto the stack.

It may serve as a discount for an item further to the left.

### Step 7: Return the answer

```java
return ans;
```

The answer array contains the final prices in their original order.

---

## 5. Dry Run

**Input:**

```text
prices = [8, 4, 6, 2, 3]
```

We begin with the last price, `3`.

| Current price | Stack action | Discount | Final price |
|---:|---|---:|---:|
| `3` | Initialize stack | None | `3` |
| `2` | Pop `3`, then push `2` | None | `2` |
| `6` | Keep `2`, then push `6` | `2` | `4` |
| `4` | Pop `6`, keep `2`, then push `4` | `2` | `2` |
| `8` | Keep `4`, then push `8` | `4` | `4` |

The processing order is right to left, but the answer remains in the original array order.

**Final answer:**

```text
[4, 2, 4, 2, 3]
```

### Stack visualization

The stack's top is shown first.

<text color="secondary" size="sm">Processing price 2</text>

```text
Before:
Top -> 3

3 > 2, so pop 3.

After:
Top -> 2
```

<text color="secondary" size="sm">Processing price 6</text>

```text
Top -> 2

2 <= 6, so 2 is a valid discount.
Final price = 6 - 2 = 4

Push 6:
Top -> 6
       2
```

<text color="secondary" size="sm">Processing price 4</text>

```text
Top -> 6

6 > 4, so pop 6.

Top -> 2

2 <= 4, so 2 is the discount.
Final price = 4 - 2 = 2
```

The stack helps us avoid repeatedly scanning all the prices to the right.

---

## 6. Edge Cases

| Case | Input | Output |
|---|---|---|
| Single item | `[5]` | `[5]` |
| Strictly increasing prices | `[1, 2, 3, 4, 5]` | `[1, 2, 3, 4, 5]` |
| Equal prices | `[5, 5, 5]` | `[0, 0, 5]` |
| Decreasing prices | `[5, 4, 3, 2]` | `[1, 1, 1, 2]` |
| No qualifying price for the first item | `[1, 2, 3]` | `[1, 2, 3]` |

**Important:** Equal prices qualify as discounts. This is why the loop uses `stack.peek() > prices[i]` rather than `stack.peek() >= prices[i]`.

---

## 7. Complexity Analysis

Let `n` be the length of `prices`.

- **Time Complexity:** `O(n)` — Although there is a `while` loop inside the `for` loop, every price is pushed onto the stack once and popped at most once.
- **Space Complexity:** `O(n)` — The stack and answer array can each store up to `n` elements.

This is more efficient than checking every item to the right of each price, which would take `O(n²)` time in the worst case.

---

## 8. Pattern Recognition

This problem uses the **next smaller or equal element** pattern with a monotonic stack.

Recognize this pattern when a problem asks you to find:

- The first smaller or equal element to the right.
- The next greater or smaller element.
- A nearest qualifying element in an array.
- A result that can be found by eliminating candidates that cannot help future elements.

### Why traverse backward?

Traversing from right to left means the stack represents potential answers among the already processed elements to the right.

The stack removes invalid candidates and exposes the nearest valid candidate at its top.

### Why is this a monotonic stack?

After removing elements greater than the current price, the remaining candidate prices are arranged in nondecreasing order from the top toward the bottom. Pushing the current price maintains this property.

---

## 9. Stack Patterns Learned So Far

| Problem | Pattern learned |
|---|---|
| Valid Parentheses | Matching opening and closing symbols |
| Min Stack | Maintaining auxiliary state |
| Evaluate Reverse Polish Notation | Operand management and evaluation |
| Daily Temperatures | Next greater element using a monotonic stack |
| Next Greater Element | Next-greater-element pattern |
| Next Greater Node In Linked List | Monotonic stack with linked-list values |
| Remove All Adjacent Duplicates In String | Adjacent-element cancellation |
| Baseball Game | Stack-based history simulation |
| Make The String Great | Case-insensitive adjacent cancellation |
| Crawler Log Folder | Folder navigation and depth tracking |
| Final Prices With a Special Discount in a Shop | Next smaller or equal element using a monotonic stack |

---

## 10. Final Mental Model

Imagine processing the prices from right to left.

For each item, ask:

1. Is the top of the stack greater than my price? If yes, remove it.
2. Is the stack empty? If yes, there is no discount.
3. Otherwise, the top is my discount.
4. Push my price so earlier items can use it.

**Key takeaway:** A monotonic stack efficiently finds the nearest qualifying element by discarding candidates that cannot provide the answer.