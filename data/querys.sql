DROP TRIGGER IF EXISTS trigger_auditoria_usuario;

CREATE TRIGGER trigger_auditoria_usuario
AFTER UPDATE ON usuario 
FOR EACH ROW
    INSERT INTO auditoria_usuario(
        id_usuario,
        accion,
        nombre_anterior,
        nombre_nuevo,
        clave_anterior,
        clave_nueva,
        usuariosistema
    ) VALUES (
        OLD.id_usuario,
        'UPDATE',
        OLD.nombre,
        NEW.nombre,
        OLD.clave,
        NEW.clave,
        USER()
    );
