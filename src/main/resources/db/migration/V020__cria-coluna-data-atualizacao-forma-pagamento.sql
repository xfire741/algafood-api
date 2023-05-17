ALTER TABLE forma_pagamento ADD data_atualizacao DATETIME NULL;
UPDATE forma_pagamento SET data_atualizacao = UTC_TIMESTAMP;
ALTER TABLE forma_pagamento MODIFY data_atualizacao DATETIME NOT NULL;