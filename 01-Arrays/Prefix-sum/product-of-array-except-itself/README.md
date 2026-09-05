# WIN #14 — Product of Array Except Self

## Problem

Given an integer array `nums`, return an array `answer` such that `answer[i]` contains the product of every element in `nums` except `nums[i]`.

The solution must run in `O(n)` time and cannot use the division operation.

For example:

```text
Input:
nums = [1,2,3,4]

Output:
[24,12,8,6]
```

For each position:

```text
answer[0] = 2 × 3 × 4 = 24

answer[1] = 1 × 3 × 4 = 12

answer[2] = 1 × 2 × 4 = 8

answer[3] = 1 × 2 × 3 = 6
```

---

## My Java Solution

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] ans = new int[nums.length];

        int leftProduct = 1;

        for (int i = 0; i < nums.length; i++) {

            if (i == 0) {
                ans[i] = leftProduct;
                leftProduct *= nums[0];
                continue;
            }

            ans[i] = leftProduct;
            leftProduct *= nums[i];
        }

        int rightProduct = 1;

        for (int i = nums.length - 1; i >= 0; i--) {

            if (i == nums.length - 1) {
                ans[i] = ans[i] * rightProduct;
                rightProduct *= nums[i];
                continue;
            }

            ans[i] = ans[i] * rightProduct;
            rightProduct *= nums[i];
        }

        return ans;
    }
}
```

---

## My Thought Process

For every element, I need the product of all elements on its left and all elements on its right.

For example:

```text
nums = [1,2,3,4]
```

For the element `3`:

```text
Elements on the left: 1 × 2 = 2

Elements on the right: 4

Answer: 2 × 4 = 8
```

So I realized that I can split the problem into two parts:

```text
Product of everything on the left

Product of everything on the right
```

I used two passes through the array.

### First Pass: Left Products

I use `leftProduct` to keep track of the product of all elements before the current index.

For:

```text
nums = [1,2,3,4]
```

The first pass builds:

```text
ans = [1,1,2,6]
```

This represents the product of all elements to the left of each position.

For example:

```text
Index 0:
Nothing on the left → 1

Index 1:
1 → 1

Index 2:
1 × 2 → 2

Index 3:
1 × 2 × 3 → 6
```

### Second Pass: Right Products

Then I traverse the array from right to left.

I use `rightProduct` to keep track of the product of all elements after the current index.

I multiply the existing value in `ans[i]` by `rightProduct`.

For example:

```text
Before second pass:

ans = [1,1,2,6]
```

After including the products from the right:

```text
ans = [24,12,8,6]
```

So every position now contains:

```text
left product × right product
```

which is exactly the product of every element except itself.

The overall approach is:

```text
First pass
    ↓
Calculate product of elements to the left
    ↓
Store it in ans
    ↓
Second pass from right
    ↓
Calculate product of elements to the right
    ↓
Multiply it with ans
    ↓
Return answer
```

---

## Pattern Recognition

### Pattern: Prefix Product + Suffix Product

This pattern is useful when the answer at each index depends on the elements before and after that index.

The general idea is:

```text
Prefix information
        +
Suffix information
        ↓
Answer for current position
```

For this problem:

```text
leftProduct  = product of elements before i

rightProduct = product of elements after i
```

Then:

```text
answer[i] = leftProduct × rightProduct
```

Instead of calculating the product separately for every index, I build the left products in one pass and the right products in another pass.

This avoids repeatedly traversing the array for each position.

Another important part is using `1` as the initial product.

For example:

```text
Product of no elements = 1
```

This allows the first element to correctly receive a left product of `1` and the last element to correctly receive a right product of `1`.

---

## Complexity

### Time Complexity

```text
O(n)
```

The array is traversed twice.

The first pass calculates the left products and the second pass calculates the right products.

Therefore:

```text
O(n) + O(n) = O(n)
```

### Space Complexity

```text
O(n)
```

The `ans` array is required to store the output.

Apart from the output array, only a constant number of variables are used.

---

## Key Learning

I learned how a problem can be divided into information coming from the left and information coming from the right.

For each position, I do not actually need the entire array.

I only need:

```text
Product of everything before the current index

Product of everything after the current index
```

Then I combine them:

```text
leftProduct × rightProduct
```

The important idea is that I can build these values incrementally instead of recalculating the product for every position.

I also learned that division is not necessary for this problem. By maintaining prefix and suffix products, I can solve it efficiently even when the array contains zeroes.

This problem helped me understand how prefix and suffix techniques can be extended beyond sums and used with other operations such as multiplication.