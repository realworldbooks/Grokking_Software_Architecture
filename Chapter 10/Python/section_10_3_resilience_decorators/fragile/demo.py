from .fragile_payment_service import FragilePaymentService



class Demo:
    @staticmethod
    def run():
        """
        THE FRAGILE DEMO COMPOSER:

            DESIGN NOTE:
                This file represents the "Junior Developer" entry point. It demonstrates
                the 'Happy Path Fallacy' in action—where we assume the outside world
                will always behave perfectly.

                ARCHITECTURAL CRITIQUE:
                    1. TIGHT COUPLING: This file must instantiate the concrete
                    'FragilePaymentService' directly. There is no Port/Abstract Base Class
                    to hide behind. Changing vendors requires a surgical strike on this file.

                    2. CASCADING FAILURE: Because the fragile service lacks retries, a single
                    "hiccup" in the FlakyPayments API causes an unhandled exception that explodes
                    here, potentially killing the entire execution thread.

                    3. DATA LOSS: There is no 'Plan B'. If the charge fails, the transaction
                    is simply forgotten. In a professional system, this is a loss of
                    revenue and customer trust.
        """
    print("\n=== Chapter 10.3: The Fragile Way (Python) ===")

    # We instantiate the liability directly.
    fragile_service = FragilePaymentService()
    amount_to_charge = 50.00

    print("--- SCENARIO: Attempting a naked call to FlakyPayments API ---")

    try:
        # This is a "Naked Call." No shield, no backoff, no
        # mercy.
        result = fragile_service.charge_credit_card(amount_to_charge)

        print("      [Fragile Result] Success! (Only because the network was stable)")
        print(f"      [Data] {result}")

    except Exception as ex:
        # In this architecture, 'Resilience' is just a catch
        # block that
        # prints a failure message while the business loses
        # money.
        print("      [SYSTEM CRASH] The transaction has failed permanently.")
        print(f"      [Reason] {type(ex).__name__}: {str(ex)}")
        print("      [Consequence] The user sees an error screen and the sale is lost.")

        # --- THE ARCHITECTURAL VERDICT ---
        print("\n" + "=" * 60)
        print("ARCHITECTURAL VERDICT: THE LIABILITY")
        print("-" * 60)
        print("COUPLING: High. The demo is married to the physical network tool.")
        print("AVAILABILITY: Brittle. Success requires 100% network uptime.")
        print("SURVIVABILITY: Zero. No retries, no timeouts, no fallback logic.")
        print("\nREALITY CHECK: This code satisfies the feature request, but it")
        print("fails as a stable system. It is a debt that will eventually come due.")
        print("=" * 60 + "\n")
