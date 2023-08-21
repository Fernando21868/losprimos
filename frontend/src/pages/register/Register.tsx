"use client";
import { Loader2 } from "lucide-react";
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
import { useSelector, useDispatch } from "react-redux";
import { StateAuthSlice } from "../../types/interfaces";
import { RootState } from "../../store/store";
import { registerUser } from "../../data/authActions";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";
import { Checkbox } from "../../@components/ui/checkbox";
import { toast } from "../../@components/ui/use-toast";
import { Toast, ToastAction } from "../../@components/ui/toast";
import { Toaster } from "../../@components/ui/toaster";

function Register() {
  const { loading, userInfo, error, success }: StateAuthSlice = useSelector(
    (state: RootState) => state["auth"]
  );
  const dispatch = useDispatch<ThunkDispatch<RootState, any, AnyAction>>();
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  useEffect(() => {
    if (userInfo) {
      navigate("/");
    }
    if (success) navigate("/login");
  }, [navigate, userInfo, success]);

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      phoneNumber: "",
      address: "",
      birthday: "",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    const response = await dispatch(registerUser(values));
  }

  function handleShowPassword() {
    setShowPassword(!showPassword);
  }

  function handleShowConfirmPassword() {
    setShowConfirmPassword(!showConfirmPassword);
  }

  return (
    <div className="p-4 flex flex-col items-center justify-center">
      {error ? <Toaster></Toaster> : ""}
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="p-4 max-w-md w-full"
        >
          <FormField
            control={form.control}
            name="firstName"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Nombres</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Lucas" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="lastName"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Apellidos</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Rodriguez" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>E-mail</FormLabel>
                <FormControl>
                  <Input
                    type="email"
                    placeholder="Por ejemplo: lucasRodriguez12@gmail.com"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem className="mb-4">
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
              <FormItem className="mb-4">
                <FormLabel>Contraseña</FormLabel>
                <FormDescription>
                  La contraseña debe cotener por lo menos 4 caracteres, 1
                  mayuscula, 1 minuscula, y 1 numero
                </FormDescription>
                <FormControl>
                  <Input
                    type={showPassword ? "text" : "password"}
                    placeholder="Por ejemplo: Lucas123"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="-mt-2 mb-4 flex items-center space-x-2">
            <Checkbox onClick={handleShowPassword} id="showPassword" />
            <label
              htmlFor="showPassword"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
            >
              Mostrar/ocultar contraseña
            </label>
          </div>
          <FormField
            control={form.control}
            name="confirmPassword"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Confirmar contraseña</FormLabel>
                <FormControl>
                  <Input
                    type={showConfirmPassword ? "text" : "password"}
                    placeholder="Por ejemplo: Lucas123"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="-mt-2 mb-4 flex items-center space-x-2">
            <Checkbox
              onClick={handleShowConfirmPassword}
              id="showConfirmPassowd"
            />
            <label
              htmlFor="showConfirmPassowd"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
            >
              Mostrar/ocultar contraseña
            </label>
          </div>
          <FormField
            control={form.control}
            name="phoneNumber"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Telefono o celular</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: 3886905902" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="birthday"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Fecha de nacimiento</FormLabel>
                <FormControl>
                  <Input
                    type="date"
                    placeholder="Por ejemplo: 3886905902"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="address"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Direccion</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Jujuy 123" {...field} />
                </FormControl>
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
            <Button
              className="mt-4"
              type="submit"
              onClick={() => {
                error
                  ? toast({
                      variant: "destructive",
                      title: "Algo salio mal.",
                      description: error,
                      action: (
                        <ToastAction altText="Try again">Try again</ToastAction>
                      ),
                    })
                  : undefined;
              }}
            >
              Registrarse
            </Button>
          )}
          <div>
            ¿Tienes una cuenta?
            <Button className="-ml-2" variant="link">
              <Link to={"/login"}>Inicia sesion</Link>
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
}

const formSchema = z
  .object({
    firstName: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    lastName: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    username: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
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
      })
      .trim(),
    confirmPassword: z
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
      })
      .trim(),
    email: z
      .string()
      .min(1, {
        message: "Debe ingresar un e-mail",
      })
      .max(100, {
        message: "El email no debe superar los 100 caracteres",
      })
      .email({
        message: "Ingrese un email valido",
      })
      .trim(),
    birthday: z.string().trim().optional(),
    phoneNumber: z.string().trim().optional(),
    address: z.string().trim().optional(),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: "custom",
        message: "The passwords did not match",
      });
    }
  });

export default Register;
