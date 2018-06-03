CREATE PROCEDURE [dbo].[saveInvoice]
@invoiceId bigint,
@date datetime,
@quantity decimal(10,3),
@price decimal(12,2),
@goodId bigint,
@vendorId bigint
AS
BEGIN
	BEGIN TRANSACTION;
    SAVE TRANSACTION saveInvoiceTran;
    BEGIN TRY
		IF @invoiceId IS NULL
			BEGIN
				INSERT INTO invoices(date_of_receiving, quantity, price, good_id, vendor_id) VALUES (@date, @quantity, @price, @goodId, @vendorId);
				INSERT INTO balance(date_of_receiving, quantity, price, good_id, invoice_id) VALUES (@date, @quantity, @price, @goodId, SCOPE_IDENTITY());
				SELECT * FROM invoices WHERE id = SCOPE_IDENTITY();
			END;
		ELSE
			BEGIN
				DECLARE @invoiceQuantity decimal(10,3);
				SELECT @invoiceQuantity = quantity FROM invoices WHERE id = @invoiceId;
				DECLARE @balanceQuantity decimal(10,3);
				SELECT @balanceQuantity = quantity FROM balance WHERE invoice_id = @invoiceId;
				UPDATE invoices SET date_of_receiving = @date, quantity = @quantity, price = @price, good_id = @goodId, vendor_id = @vendorId WHERE id = @invoiceId;
				UPDATE balance SET date_of_receiving = @date, quantity = @balanceQuantity + (@quantity - @invoiceQuantity), price = @price, good_id = @goodId WHERE invoice_id = @invoiceId;
				SELECT * FROM invoices WHERE id = @invoiceId;
			END
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION saveInvoiceTran;
        END
    END CATCH
    COMMIT TRANSACTION 
END
