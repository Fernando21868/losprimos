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
import { Link } from "react-router-dom";

function Register() {
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

  // 2. Define a submit handler.
  function onSubmit(values: z.infer<typeof formSchema>) {
    // Do something with the form values.
    // ✅ This will be type-safe and validated.
    console.log(values);
  }

  return (
    <div className="p-4 flex flex-col items-center justify-center">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="p-4 max-w-md w-full"
        >
          <FormField
            control={form.control}
            name="firstName"
            render={({ field }) => (
              <FormItem>
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
              <FormItem>
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
              <FormItem>
                <FormLabel>E-mail</FormLabel>
                <FormControl>
                  <Input
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
          <FormField
            control={form.control}
            name="confirmPassword"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Confirmar contraseña</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Lucas123" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="phoneNumber"
            render={({ field }) => (
              <FormItem>
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
              <FormItem>
                <FormLabel>Fecha de nacimiento</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: 3886905902" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="address"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Direccion</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Jujuy 123" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <Button className="mt-4" type="submit">
            Registrarse
          </Button>
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
      }),
    lastName: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      }),
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
      }),
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
      }),
    birthday: z
      .string().optional(),
    phoneNumber: z
      .string()
      .optional(),
    address: z
      .string()
      .optional(),
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
