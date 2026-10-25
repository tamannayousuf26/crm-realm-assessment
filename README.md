# CRM Realm Assessment

This is my submission for the Junior Software Engineer Intern assessment.

- `java/` - the lead tracker console app (Part A)
- `javascript/` - the 3 JS problems (Part B)
- `sql/` - the SQL queries (Part C)

I didn't use any libraries, you just need Java and Node installed.

## How to run

### Java

```bash
cd java
javac -d out src/*.java
java -cp out Main
```

To run my tests:

```bash
java -cp out LeadTrackerTest
```

I couldn't use JUnit because the task said no third-party libraries, so I wrote a small `check()` method that prints PASS or FAIL.

The code is split into 3 files. `Lead` is the lead itself, `LeadTracker` keeps the list and checks the input, and `Main` is just the menu. I put the validation in `LeadTracker` and not in `Main` so I could test it without typing in the console every time.

Priority is not saved anywhere. `getPriority()` calls `calculatePriority(score)` every time, so it always matches the score.

### JavaScript

```bash
cd javascript
node --test
```

I used Node's built in test runner so there is nothing to install.

### SQL

The queries are in `sql/queries.sql`. I wrote them for PostgreSQL and I assumed the `leads` table already exists and score is never null.

## How I solved the JS problems

**1. firstNonRepeatingChar**

I loop through the string once and count every character in lowercase with a Map. Then I loop through the original string again and return the first character that has count 1. Because the second loop uses the original string, the answer keeps its original case, like `"sTreSS"` gives `"T"`.

For spaces I decided to skip them, because I don't think a space is a good answer. So `"aa bb"` gives null. Punctuation and numbers I count like normal characters since the question says character and not letter, so `"aa!bb"` gives `"!"`.

**2. findTwoSum**

First I thought of two nested loops, but that is O(n²). So I used a Map instead where I save each number and its index. For every number I check if `target - number` is already in the Map. If it is, I found the pair. I check before I add the current number, that way the same element can't be used twice. `[3], 6` gives null but `[3, 3], 6` gives `[0, 1]`. This way it's only one loop, O(n).

**3. countLeadsByStatus**

I loop over the leads and add 1 to `result[lead.status]`. I didn't hard code any status, so if a new one like `"LOST"` comes it still works. I only read the array and don't change it (I have a test for that). If a lead has no status I just skip it.

To show statuses with 0, you can pass a list of all statuses as the second argument. They start at 0 before counting, so `countLeadsByStatus(leads, ["NEW", "CONTACTED"])` will show `CONTACTED: 0` even if no lead has it.

## SQL

Query 1 is just `WHERE score >= 80`.

For query 2 I used `CASE` with `GROUP BY`. When I tested it I noticed that if a group has no leads it doesn't show at all. So I also added 2b, where I write the 3 groups myself and LEFT JOIN the leads, so an empty group shows 0. 2c shows all three counts in one row.

## Test cases

Java (25 checks, all pass):
- scores 0 and 49 are COLD, 50 and 79 are WARM, 80 and 100 are HOT
- empty name, name with only spaces, empty email are rejected
- score -1, 101, "abc", "50.5" and empty score are rejected
- score 0 and 100 are accepted
- spaces around name and email get trimmed
- view leads with nothing added shows "No leads yet"

JavaScript (14 tests, all pass):
- all the examples from the assessment
- `"sTreSS"` gives `"T"`, `"aA"` gives null
- `"aa bb"` gives null, `"aa!bb"` gives `"!"`
- `findTwoSum([3], 6)` gives null, so the same element is not used twice
- negative numbers and zero, like `[-3, 4, 3, 90], 0` gives `[0, 2]`
- empty array, null, or wrong input gives null
- empty leads array gives `{}`, a new status gets counted, original array is not changed

## A decision I made

For the score I read the whole line as text and parse it myself instead of using `scanner.nextInt()`. When I tried `nextInt()` and typed letters it crashed, and it also leaves the newline behind which messes up the next input. This way I can show a proper message and the menu keeps going.

## What I would improve

Right now the email only has to be not empty, which is what the task asked. If I had more time I would add a basic email format check and stop the same email being added twice. I would also save the leads to a file so they don't get lost when you close the app.

## AI usage

I used Claude (AI assistant) to help me write the code, the tests and this README, and to think about edge cases like the spaces in problem 1 and the empty groups in the SQL. I went through all the code, ran all the tests myself and made sure I understand everything.
