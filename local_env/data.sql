INSERT INTO public.user (uuid, username, password)
    VALUES ('a4964fc2-7adc-4fd8-b055-abd3a75acc54', 'admin', '$2a$10$e8V1PNJmpqXbKxlUbzInI.iqRXV2Zy9usVFkl8Nn1FOu6D3lpPRTK');
--Username: admin
--Decrypted password: A2evd5V

INSERT INTO public.user_role (user_uuid, role_id)
    VALUES ('a4964fc2-7adc-4fd8-b055-abd3a75acc54', 1),
    ('a4964fc2-7adc-4fd8-b055-abd3a75acc54', 2);
