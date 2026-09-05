# WIN #16 — First Missing Positive

## Problem

Given an unsorted integer array `nums`, return the **smallest positive integer** that is not present in the array.

The solution must run in:

```text
O(n) time
O(1) auxiliary space
```

For example:

```text
Input:
nums = [1,2,0]

Output:
3
```

Because:

```text
1 → present
2 → present
3 → missing
```

Another example:

```text
Input:
nums = [3,4,-1,1]

Output:
2
```

Here:

```text
1 → present
2 → missing
3 → present
4 → present
```

Another example:

```text
Input:
nums = [7,8,9,11,12]

Output:
1
```

Since `1` itself is missing, it is the smallest missing positive integer.

---

## My Java Solution

```java
class Solution {
    public int firstMissingPositive(int[] nums) {

        int n = nums.length;
        int i = 0;

        while(i < n){
            int number = nums[i];

            // If number is valid
            if(nums[i] >= 1 && nums[i] <= n){

                // If number is not placed in proper position
                if(nums[i] != nums[nums[i] - 1]){
                    int temp = nums[i];
                    nums[i] = nums[temp - 1];
                    nums[temp - 1] = temp;
                }

                // If number is already placed in correct position then just move
                else{
                    i++;
                }
            }

            else{
                i++;
            }
        }

        for(int j = 0; j < n; j++){
            if(nums[j] != j + 1){
                return j + 1;
            }
        }

        return n + 1;
    }
}
```

---

## My Thought Process

The first thing I noticed is that the problem asks for the **smallest positive integer** that is missing.

A simple approach would be to use a `HashSet` and store all the numbers, then check:

```text
1
2
3
4
...
```

until I find a number that does not exist.

However, that would require `O(n)` extra space.

The problem specifically requires:

```text
O(1) auxiliary space
```

So I cannot use another data structure such as a `HashSet` or an additional array.

I also need:

```text
O(n) time
```

which means I cannot repeatedly search through the array for every positive integer.

So I needed a way to use the **array itself as a data structure**.

The key observation is that for an array of length `n`, the answer must be somewhere in the range:

```text
1 to n + 1
```

Why?

If every number from:

```text
1 to n
```

is present, then the first missing positive number must be:

```text
n + 1
```

For example:

```text
nums = [1,2,3,4]

n = 4

1,2,3,4 are all present

Therefore:
answer = 5
```

This means I only care about values between:

```text
1 and n
```

Any value that is:

```text
<= 0
```

or:

```text
> n
```

cannot be the answer while there are still values in the range `1...n` that may be missing.

This led me to the idea of placing every valid number in its **correct index**.

---

## The Main Idea: Put Every Number in Its Correct Position

I use the array itself to create a relationship between a number and its index.

The desired arrangement is:

```text
Number 1 → index 0
Number 2 → index 1
Number 3 → index 2
Number 4 → index 3
...
```

In general:

```text
number x → index x - 1
```

So if I see:

```text
nums[i] = 3
```

then `3` belongs at:

```text
index = 3 - 1
      = 2
```

For example:

```text
nums = [3,4,-1,1]
```

I want to rearrange the valid numbers so that eventually the array looks something like:

```text
[1, -1, 3, 4]
```

The exact arrangement of invalid values does not matter.

What matters is:

```text
index 0 → 1
index 1 → 2
index 2 → 3
index 3 → 4
```

Then I can simply scan the array and find the first index where this relationship breaks.

---

## Step 1: Identify Valid Numbers

I only need to place numbers satisfying:

```java
if(nums[i] >= 1 && nums[i] <= n)
```

Why only this range?

Because for an array of length `n`, the only numbers that can directly occupy the required positions are:

```text
1, 2, 3, ..., n
```

For example:

```text
nums = [3,4,-1,1]
n = 4
```

Valid values:

```text
3 ✓
4 ✓
-1 ✗
1 ✓
```

The `-1` can be ignored because it cannot be the smallest positive answer.

---

## Step 2: Find the Correct Position

For every valid number:

```text
number = nums[i]
```

its correct index is:

```text
number - 1
```

For example:

```text
number = 3

correct index = 3 - 1
              = 2
```

So I check whether the number is already in its correct position.

```java
if(nums[i] != nums[nums[i] - 1])
```

If it is not in the correct position, I swap it into that position.

---

## Step 3: Place the Number Using Swapping

The important part of my solution is:

```java
int temp = nums[i];

nums[i] = nums[temp - 1];

nums[temp - 1] = temp;
```

Suppose:

```text
nums = [3,4,-1,1]
```

At index `0`:

```text
nums[0] = 3
```

The correct position of `3` is:

```text
3 - 1 = 2
```

So I move `3` to index `2`.

Before:

```text
[3,4,-1,1]
 ↑     ↑
 i     correct position
```

After swapping:

```text
[-1,4,3,1]
```

Now `3` is correctly positioned:

```text
index 2 → 3
```

The next value at index `0` is `-1`, which is invalid, so I move forward.

---

## Why I Don't Always Increment `i`

One subtle part of the solution is that after performing a swap, I **do not immediately increment `i`**.

For example:

```text
[3,4,-1,1]
```

At index `0`, I move `3` to its correct position.

The array becomes:

```text
[-1,4,3,1]
```

The value at index `0` has changed.

So I need to check the new value at index `0` before moving forward.

That is why the code does:

```java
if(nums[i] != nums[nums[i] - 1]){
    // swap
}
else{
    i++;
}
```

After a swap:

```text
i stays the same
```

After the current position is already correct, or contains an invalid value:

```text
i++
```

This allows me to keep processing the same index until the value there is either:

```text
correctly positioned
```

or:

```text
invalid
```

---

## Handling Duplicate Values

Duplicates are another important part of this problem.

Consider:

```text
nums = [1,1]
```

Both values are valid because:

```text
1 >= 1
1 <= n
```

But there is no point repeatedly swapping identical values.

This is why I check:

```java
if(nums[i] != nums[nums[i] - 1])
```

If the target position already contains the same number, I do not swap.

For example:

```text
nums = [1,1]
```

At index `1`:

```text
nums[1] = 1

correct index = 1 - 1 = 0
```

But:

```text
nums[0] = 1
```

So:

```text
nums[i] == nums[nums[i] - 1]
```

There is nothing useful to swap.

I simply move to the next index.

This is important because otherwise duplicate values could cause an unnecessary infinite swapping loop.

---

## Complete Example Walkthrough

Consider:

```text
nums = [3,4,-1,1]
```

The length is:

```text
n = 4
```

The valid range is:

```text
1 to 4
```

### Start

```text
[3,4,-1,1]
 ↑
 i
```

`3` is valid.

Its correct index is:

```text
3 - 1 = 2
```

Swap `3` into index `2`:

```text
[-1,4,3,1]
 ↑
 i
```

We keep `i` at `0`.

Now:

```text
nums[0] = -1
```

`-1` is invalid, so:

```text
i++
```

Now:

```text
[-1,4,3,1]
    ↑
    i
```

`4` is valid.

Its correct index is:

```text
4 - 1 = 3
```

Swap:

```text
[-1,1,3,4]
    ↑
    i
```

Again, we do not immediately increment `i`.

Now:

```text
nums[1] = 1
```

`1` belongs at:

```text
index = 1 - 1 = 0
```

Swap:

```text
[1,-1,3,4]
    ↑
    i
```

Now `nums[1] = -1`, which is invalid.

So:

```text
i++
```

Now the array is:

```text
[1,-1,3,4]
```

The rearrangement phase is complete.

---

## Second Pass: Find the First Missing Positive

Now I scan the array:

```java
for(int j = 0; j < n; j++){
    if(nums[j] != j + 1){
        return j + 1;
    }
}
```

The expected arrangement is:

```text
index 0 → 1
index 1 → 2
index 2 → 3
index 3 → 4
```

Our array is:

```text
[1,-1,3,4]
```

Check index `0`:

```text
nums[0] = 1
expected = 1

Correct ✓
```

Check index `1`:

```text
nums[1] = -1
expected = 2

Incorrect ✗
```

Therefore:

```text
answer = 2
```

And that is the smallest missing positive integer.

---

## Why Return `n + 1`?

At the end, I use:

```java
return n + 1;
```

This handles the case where every positive integer from `1` through `n` is present.

For example:

```text
nums = [1,2,3]
```

After rearrangement:

```text
[1,2,3]
```

Every position is correct:

```text
index 0 → 1 ✓
index 1 → 2 ✓
index 2 → 3 ✓
```

There is no missing positive number between `1` and `n`.

Therefore the first missing positive is:

```text
n + 1 = 4
```

So:

```text
[1,2,3] → 4
```

---

## Pattern Recognition

### Pattern: Cyclic Sort / Index Placement

This problem is a classic example of the **Cyclic Sort / Correct Index Placement** pattern.

The main idea is:

```text
Value → Correct Index
```

For this problem:

```text
1 → index 0
2 → index 1
3 → index 2
...
n → index n-1
```

Or generally:

```text
correct index = value - 1
```

Instead of using an additional data structure to remember which numbers exist, I use the input array itself to place each valid number in its correct position.

The overall process is:

```text
Original Array
      ↓
Find valid number
      ↓
Calculate correct index
      ↓
Swap number into correct position
      ↓
Repeat until every possible number is positioned
      ↓
Scan array
      ↓
First incorrect position = missing number
```

This pattern is especially useful when:

```text
1. The array contains numbers in a known range

2. The required answer depends on whether values exist

3. Extra space must be O(1)

4. The array can be modified
```

A very important recognition clue is:

```text
Numbers are in the range 1...n
```

When I see this kind of constraint, I should immediately think about using the **index as a representation of the value**.

---

## Why This Approach Is Better Than a HashSet

A `HashSet` solution could be easier to write:

```text
Store every number
      ↓
Check 1
      ↓
Check 2
      ↓
Check 3
      ↓
Find missing number
```

But that requires:

```text
O(n) extra space
```

The problem requires:

```text
O(1) auxiliary space
```

So instead, I reuse the input array.

The array itself becomes a kind of lookup structure:

```text
index 0 → tells me whether 1 exists
index 1 → tells me whether 2 exists
index 2 → tells me whether 3 exists
...
```

This is the key trick that makes the solution satisfy the space requirement.

---

## Complexity

### Time Complexity

```text
O(n)
```

The first phase rearranges the numbers using swaps.

Although there is a `while` loop, each successful swap places a valid number into its correct position.

The array is then scanned once more to find the first incorrect position.

Therefore, the overall complexity is:

```text
O(n)
```

---

### Space Complexity

```text
O(1)
```

The solution modifies the input array directly.

It only uses a constant number of variables:

```text
n
i
number
temp
j
```

No `HashSet`, auxiliary array, or other data structure is used.

Therefore:

```text
Auxiliary Space = O(1)
```

---

## Folder Structure

**Problem Number:** 41
**Problem Name:** First Missing Positive

**Category:** Arrays
**Pattern:** Cyclic Sort / Index Placement

**Folder:**

```text
01-Arrays/Cyclic-Sort/first-missing-positive/
```

---

## Key Learning

I learned that sometimes the **index of an array can be used as extra information** instead of creating another data structure.

The most important idea from this problem is:

```text
For a value x:

Correct index = x - 1
```

So instead of asking:

```text
"Does this number exist somewhere in the array?"
```

I rearrange the array so that I can ask:

```text
"Is the expected number present at its correct index?"
```

The overall strategy is:

```text
Valid number
     ↓
Place it at index number - 1
     ↓
Repeat
     ↓
Scan from left to right
     ↓
First index where nums[index] != index + 1
     ↓
index + 1 is the answer
```

I also learned that values outside the range:

```text
1...n
```

can effectively be ignored because they cannot occupy the positions needed to determine the smallest missing positive.

The biggest takeaway for me is recognizing the **Cyclic Sort / Index Placement pattern**.

Whenever I see an array containing numbers that belong to a known range and the problem asks about missing, duplicate, or misplaced values, I should consider:

```text
Can I use the array indices
as a representation of the values?
```

This problem is a great example of achieving:

```text
O(n) Time
O(1) Auxiliary Space
```

by using the input array itself as part of the algorithm.
