# Seed data

A snapshot of one state's subsidy depots, farmers and reservations, taken at **2026-07-10T06:30:00Z**. When you produce a report from this data, use that instant as "now".

All timestamps are UTC, in ISO-8601 (`2026-07-10T06:30:00Z`). All files are comma-separated with a header row and no quoting.

| File | Columns |
| --- | --- |
| `depots.csv` | `depot_id`, `district`, `name` |
| `skus.csv` | `sku`, `product`, `description`, `unit_kg` (bag size) |
| `products.csv` | `product`, `kg_per_acre` (quota requirement per acre of landholding) |
| `schemes.csv` | `scheme_id`, `season`, `opens_at`, `closes_at` |
| `farmers.csv` | `farmer_id`, `district`, `landholding_acres` |
| `depot_stock.csv` | `depot_id`, `sku`, `scheme_id`, `total` (bags allocated to that depot for that scheme) |
| `reservations.csv` | One row per reservation line. A reservation that took several SKUs has one row per SKU, sharing `reservation_id`, `idempotency_key`, `status` and timestamps. Columns: `reservation_id`, `idempotency_key`, `farmer_id`, `depot_id`, `sku`, `scheme_id`, `qty`, `status`, `created_at`, `expires_at`, `confirmed_at` |
| `expected_report.csv` | The output your report must reproduce on this data. |

`status` is one of `HELD`, `CONFIRMED`, `CANCELLED`, `EXPIRED`. `confirmed_at` is empty unless the reservation is `CONFIRMED`. Not every depot stocks every SKU.

The data is a snapshot: it reflects whatever state the system was in at that instant.
