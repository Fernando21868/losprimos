"use client";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Button } from "../../@components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../@components/ui/form";
import { Input } from "../../@components/ui/input";
import { Link, useNavigate } from "react-router-dom";
import { RootState } from "../../store/store";
import { useSelector } from "react-redux";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import { useDispatch } from "react-redux";
import { userLogin } from "../../data/authActions";
import { Loader2 } from "lucide-react";
import LayoutClient from "../../@components/layoutClient/LayoutClient";
import { useEffect } from "react";

function Login() {
  const { loading, error, userInfo } = useSelector(
    (state: RootState) => state["auth"]
  );
  const dispatch = useDispatch<ThunkDispatch<RootState, any, AnyAction>>();
  const navigate = useNavigate();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  // 2. Define a submit handler.
  function onSubmit(values: z.infer<typeof formSchema>) {
    dispatch(userLogin(values));
    navigate("/");
  }

  useEffect(() => {
    if (userInfo) {
      navigate("/");
    }
  });

  return (
    <div className="p-4 flex flex-col items-center justify-center">
      {error && "error"}
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="p-4 max-w-md w-full"
        >
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Usuario</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Usuario" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Contraseña</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Lucas123" {...field} />
                </FormControl>
                <FormDescription>
                  La contraseña debe cotener por lo menos 4 caracteres, 1
                  mayuscula, 1 minuscula, y 1 numero
                </FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
          {loading ? (
            <Button className="mt-4" disabled>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              Por favor espere
            </Button>
          ) : (
            <Button className="mt-4" type="submit">
              Iniciar sesion
            </Button>
          )}
          <div>
            ¿No tesnes una cuenta todavia?
            <Button className="-ml-2" variant="link">
              <Link to={"/register"}>Registrate</Link>
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
}

const formSchema = z.object({
  username: z
    .string()
    .min(1, {
      message: "Debe ingresar un nombre de usuario",
    })
    .max(30, {
      message: "El nombre de usuario no debe superar los 30 caracteres",
    }),
  password: z
    .string()
    .min(1, {
      message: "Debe ingresar una contraseña",
    })
    .max(30, {
      message: "La contraseña no debe superar los 30 caracteres",
    })
    .regex(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{4,}$/, {
      message:
        "La contraseña debe cotener por lo menos 4 caracteres, 1 mayuscula, 1 minuscula, y 1 numero",
    }),
});

export default Login;
