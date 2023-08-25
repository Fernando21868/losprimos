import { useState } from "react";
import AlertMessage from "../../@components/alertMessage/AlertMessage";

export default function ConfirmationEmail() {
  const [closeAlertMessage, setCloseAlertMessage] = useState(true);
  return (
    <div className="flex flex-col max-w-lg items-center justify-center w-full mx-auto p-4">
      {closeAlertMessage ? <AlertMessage
        description="Su cuenta ha sido registrada con exito"
        setCloseAlertMessage={setCloseAlertMessage}
        title="Exito"
        buttonText="Cerrar"
      ></AlertMessage> : ""}
      <h1 className="my-4 text-5xl text-center">Su cuenta ha sido registrada con exito!!!</h1>
      <h2 className="my-4 text-3xl text-center">Se ha enviado un mail o correo a su cuenta de correo electronico con la que sea ha registrado</h2>
      <h3 className="my-4 text-xl text-center">Haga click en el enlace que dice <span className="mt-4 underline">VERIFICAR</span> para terminar con el proceso de registro</h3>
    </div>
  );
}
