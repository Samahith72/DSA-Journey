# WIN #5 — Valid Palindrome

## Problem

Given a string `s`, determine whether it is a palindrome.

A string is considered a palindrome after:

1. Converting all uppercase letters to lowercase.
2. Removing all non-alphanumeric characters.

After processing the string, it should read the same from left to right and from right to left.

For example:

```text
Input: "A man, a plan, a canal: Panama"

Processed String: "amanaplanacanalpanama"

Output: true
```

---

## My Java Solution

```java
class Solution {
    public boolean isPalindrome(String s) {

        if (s.length() == 0 || s.length() == 1) {
            return true;
        }

        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }

        String s1 = sb.toString();

        int left = 0;
        int right = s1.length() - 1;

        while (left < right) {
            char c1 = s1.charAt(left);
            char c2 = s1.charAt(right);

            if (c1 != c2) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

---

## My Thought Process

The first thing I noticed was that I cannot directly compare the original string because uppercase letters and non-alphanumeric characters should be ignored.

So I first created a cleaned version of the string.

While going through every character:

1. I checked whether the character was a letter or a digit.
2. If it was alphanumeric, I converted it to lowercase.
3. I added it to a new string.

After creating the cleaned string, the problem became a simple palindrome check.

To check whether a string is a palindrome, I used two pointers:

```text
left → starts from the beginning

right → starts from the end
```

I compared the characters at both positions.

If the characters were different, the string was not a palindrome.

If they were the same, I moved both pointers towards the center.

The process continues until the pointers meet.

The overall approach is:

```text
Original String
        ↓
Remove non-alphanumeric characters
        ↓
Convert characters to lowercase
        ↓
Create a cleaned string
        ↓
Use two pointers from both ends
        ↓
Compare characters
        ↓
Return true if all characters match
```

---

## Pattern Recognition

### Pattern: Two Pointers

This pattern is useful when I need to compare or process elements from two different positions.

For palindrome problems, the two pointers usually start at opposite ends.

The general approach is:

```text
left = beginning

right = end

while left < right:

    Compare the elements.

    If they are different:
        Return false.

    Move left forward.

    Move right backward.
```

For this problem:

```text
left → beginning of the cleaned string

right → end of the cleaned string
```

If every pair of characters matches, the string is a palindrome.

---

## Complexity

### Time Complexity

```text
O(n)
```

The string is traversed once to create the cleaned version.

The cleaned string is then traversed again using two pointers.

Both operations are linear, so the overall time complexity is:

```text
O(n)
```

### Space Complexity

```text
O(n)
```

A new string is created to store the cleaned version of the original string.

The extra space required can grow based on the size of the input string.

---

## Key Learning

I learned that sometimes it is easier to first transform the input into a simpler form before solving the actual problem.

In this problem, instead of handling uppercase letters and special characters while comparing characters, I first created a cleaned string.

After that, the problem became a standard palindrome check.

The main pattern I practiced was Two Pointers.

The important idea was:

```text
Start from both ends.

Compare the characters.

Move towards the center.

Stop immediately if a mismatch is found.
```

This problem also helped me understand that preprocessing the input can sometimes make the main logic simpler and easier to understand.