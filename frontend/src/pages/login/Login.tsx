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
import { useEffect, useState } from "react";
import { Checkbox } from "../../@components/ui/checkbox";
import { toast } from "../../@components/ui/use-toast";
import { ToastAction } from "../../@components/ui/toast";
import { Toaster } from "../../@components/ui/toaster";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "../../@components/ui/card";

function Login() {
  const [showPassword, setShowPassword] = useState(false);

  const { loading, error, userInfo } = useSelector(
    (state: RootState) => state.auth
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
  async function onSubmit(values: z.infer<typeof formSchema>) {
    const response = await dispatch(userLogin(values));
    if (response.type === "auth/login/rejected") {
      toast({
        variant: "destructive",
        title: "Algo ocurrio mal.",
        description: response.payload as string,
        action: (
          <ToastAction className="left-0 top-0" altText="Cerrar">
            Intentar nuevamente
          </ToastAction>
        ),
      });
    } else {
      navigate("admin");
    }
  }

  function handleShowPassword() {
    setShowPassword(!showPassword);
  }

  useEffect(() => {
    if (userInfo) {
      navigate("admin");
    }
  }, [userInfo]);

  return (
    <div className="p-4 flex flex-col items-center justify-center h-screen w-screen">
      <Card>
        <CardHeader>
          <CardTitle>Iniciar Sesion</CardTitle>
          <CardDescription>Formulario de inicio de sesion.</CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(onSubmit)}
              className="max-w-md w-full"
            >
              <Toaster></Toaster>
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
                      <Input
                        type={showPassword ? "text" : "password"}
                        placeholder="Por ejemplo: Lucas123"
                        {...field}
                      />
                    </FormControl>
                    <FormDescription>
                      La contraseña debe cotener por lo menos 4 caracteres, 1
                      mayuscula, 1 minuscula, y 1 numero
                    </FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="mt-2 mb-4 flex items-center space-x-2">
                <Checkbox onClick={handleShowPassword} id="showPassword" />
                <label
                  htmlFor="showPassword"
                  className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                >
                  Mostrar/ocultar contraseña
                </label>
              </div>
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
            </form>
          </Form>
        </CardContent>
        <CardFooter>
          <div>
            ¿Olvidaste la contraseña?
            <Button className="-ml-2" variant="link">
              <Link to={"/register"}>Solicitar recuperar cuenta</Link>
            </Button>
          </div>
        </CardFooter>
      </Card>
    </div>
  );
}

const formSchema = z.object({
  username: z
    .string()
    .nonempty({
      message: "Debe ingresar un nombre de usuario",
    })
    .max(30, {
      message: "El nombre de usuario no debe superar los 30 caracteres",
    }),
  password: z
    .string()
    .nonempty({
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
