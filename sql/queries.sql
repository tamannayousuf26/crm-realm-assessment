-- PostgreSQL
-- Assumption: score is never NULL and always between 0 and 100.

-- 1. HOT leads
SELECT id, name, email, score
FROM leads
WHERE score >= 80
ORDER BY score DESC, id;


-- 2. Number of leads in each priority group
SELECT
    CASE
        WHEN score >= 80 THEN 'HOT'
        WHEN score >= 50 THEN 'WARM'
        ELSE 'COLD'
    END AS priority,
    COUNT(*) AS lead_count
FROM leads
GROUP BY priority
ORDER BY MIN(score) DESC;


-- 2b. Same as 2, but a group with no leads still shows up with 0
SELECT
    p.priority,
    COUNT(l.id) AS lead_count
FROM (VALUES
        ('HOT',  80, 100, 1),
        ('WARM', 50,  79, 2),
        ('COLD',  0,  49, 3)
     ) AS p(priority, min_score, max_score, sort_order)
LEFT JOIN leads l
       ON l.score BETWEEN p.min_score AND p.max_score
GROUP BY p.priority, p.sort_order
ORDER BY p.sort_order;


-- 2c. All counts in one row (FILTER is Postgres only, in MySQL use SUM(CASE ...))
SELECT
    COUNT(*) FILTER (WHERE score >= 80)             AS hot,
    COUNT(*) FILTER (WHERE score BETWEEN 50 AND 79) AS warm,
    COUNT(*) FILTER (WHERE score < 50)              AS cold
FROM leads;
