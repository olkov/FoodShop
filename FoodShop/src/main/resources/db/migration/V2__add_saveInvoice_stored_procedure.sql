CREATE PROCEDURE saveInvoice
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
		IF @invoiceId = 0
			BEGIN
				DECLARE @id table (ID bigint);
				DECLARE @insertedId bigint;
				INSERT INTO invoices(date_of_receiving, quantity, price, good_id, vendor_id) OUTPUT inserted.id INTO @id VALUES (@date, @quantity, @price, @goodId, @vendorId);
				SELECT @insertedId = ID FROM @id;
				INSERT INTO balance(date_of_receiving, quantity, price_per_unit, good_id, invoice_id) VALUES (@date, @quantity, @price/@quantity, @goodId, @insertedId);
				SELECT * FROM invoices WHERE id = @insertedId;
			END;
		ELSE
			BEGIN
				DECLARE @invoiceQuantity decimal(10,3);
				SELECT @invoiceQuantity = quantity FROM invoices WHERE id = @invoiceId;
				DECLARE @balanceQuantity decimal(10,3);
				SELECT @balanceQuantity = quantity FROM balance WHERE invoice_id = @invoiceId;
				DECLARE @newQuantity decimal(10,3);
				SET @newQuantity = @balanceQuantity + (@quantity - @invoiceQuantity);
				UPDATE invoices SET date_of_receiving = @date, quantity = @quantity, price = @price, good_id = @goodId, vendor_id = @vendorId WHERE id = @invoiceId;
				UPDATE balance SET date_of_receiving = @date, quantity = @newQuantity, price_per_unit = @price/@newQuantity, good_id = @goodId WHERE invoice_id = @invoiceId;
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
