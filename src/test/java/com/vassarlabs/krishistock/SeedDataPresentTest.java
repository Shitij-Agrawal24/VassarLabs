package com.vassarlabs.krishistock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** Checks that the seed files are where the build expects them. */
class SeedDataPresentTest {

    private static final Path SEED = Path.of("data", "seed");

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "depots.csv          | depot_id,district,name",
        "skus.csv            | sku,product,description,unit_kg",
        "products.csv        | product,kg_per_acre",
        "schemes.csv         | scheme_id,season,opens_at,closes_at",
        "farmers.csv         | farmer_id,district,landholding_acres",
        "depot_stock.csv     | depot_id,sku,scheme_id,total",
        "reservations.csv    | reservation_id,idempotency_key,farmer_id,depot_id,sku,scheme_id,qty,status,created_at,expires_at,confirmed_at",
        "expected_report.csv | district,sku,total_stock,units_issued,units_held,hold_expiry_rate,distinct_farmers_served,top3_depots_by_sell_through",
    })
    void seedFileHasExpectedHeader(String file, String header) throws IOException {
        List<String> lines = Files.readAllLines(SEED.resolve(file));

        assertEquals(header, lines.getFirst());
        assertTrue(lines.size() > 1, file + " has no data rows");
    }
}
